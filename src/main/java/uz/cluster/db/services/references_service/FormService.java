package uz.cluster.db.services.references_service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.cluster.annotation.CheckPermission;
import uz.cluster.db.composite_keys.FormDao;
import uz.cluster.db.entity.auth.RoleFormPermission;
import uz.cluster.db.entity.auth.User;
import uz.cluster.db.entity.forms.RecentForm;
import uz.cluster.db.entity.forms.StaredForm;
import uz.cluster.db.entity.references.model.*;
import uz.cluster.db.repository.forms.RecentFormRepository;
import uz.cluster.db.repository.forms.StaredFormRepository;
import uz.cluster.db.repository.references.FormRepository;
import uz.cluster.db.repository.references.StatusRepository;
import uz.cluster.db.types.Nls;
import uz.cluster.enums.auth.Action;
import uz.cluster.enums.auth.SystemRoleName;
import uz.cluster.enums.forms.FormEnum;
import uz.cluster.payload.response.ApiResponse;
import uz.cluster.util.LanguageManager;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Abdrimov Usmonbek, Makhsudov Musajon
 * @version 1.1.0 (added new functions,  Recent form and Stared form)
 * @apiNote http://localhost:8889/api/menu-system
 */

@Service
@RequiredArgsConstructor
public class FormService {
    private final FormRepository formRepository;
    private final StaredFormRepository staredFormRepository;
    private final RecentFormRepository recentFormRepository;
    private final StatusRepository statusRepository;

    @CheckPermission(form = FormEnum.FORM_LIST, permission = Action.CAN_VIEW)
    public List<FormDao> getAll() {

        List<Form> repositoryAll = formRepository.getForms();
        List<FormDao> formDaoList = new ArrayList<>();
        for (uz.cluster.db.entity.references.model.Form form : repositoryAll
        ) {
            FormDao formDao = new FormDao();
            formDao.copy(form);
            formDao.init();
            formDaoList.add(formDao);
        }
        return formDaoList;

    }

    @CheckPermission(form = FormEnum.FORM_LIST, permission = Action.CAN_ADD)
    @Transactional
    public ApiResponse update(FormDao formDao) {
        formDao.prepare();
        Optional<Form> form = formRepository.findById(formDao.getId());

        if (form.isEmpty()) {
            return new ApiResponse(true, LanguageManager.getLangMessage("not saved"));
        }

        Form finalOrigin = form.get();
        finalOrigin.copy(formDao);
        if (form.get().getParentFormId() != 0){
            form.get().setParentForm(formRepository.getOne(form.get().getParentFormId()));
        }
        formRepository.save(finalOrigin);
        return new ApiResponse(true, LanguageManager.getLangMessage("saved"));
    }

    public FormDao getFormById(int id) {
        Form form = formRepository.findById(id).orElse(new Form());
        FormDao formDao = new FormDao();
        formDao.copy(form);
        formDao.init();
        return formDao;
    }

    public List<Form> getAllFormLikeTree(User user) {
        List<Form> formList = formRepository.getForms();
        List<StaredForm> staredFormList = staredFormRepository.findAllByUserId(user.getId());
        List<RecentForm> recentFormList = recentFormRepository.findAllByUserId(user.getId());
        List<Form> recentForms = new ArrayList<>();
        List<Form> staredForms = new ArrayList<>();

        if (!staredFormList.isEmpty()) {
            for (StaredForm staredForm : staredFormList) {
                for (Form form : formList) {
                    if (staredForm.getForm().getId() == form.getId()) {
                        form.setStarred(true);
                        form.setStarredAt(staredForm.getStaredAt());
                        break;
                    }
                }
                staredForms.add(staredForm.getForm());
            }

            Form staredFormParent = new Form(
                    2000,
                    "2000",
                    new Nls("Избранные формы", "Севимли формалар", "Saralangan formalar", " Favorite forms", "Saralangan formalar"),
                    new Status("A", new Nls("Активный", "Фаол", "Faol", "Active", "Faol")),
                    2000,
                    null,
                    staredForms,
                    null,
                    new Nls("Избранные формы", "Севимли формалар", "Sevimli formalar", " Favorite forms", "Sevimli formalar").getActiveLanguage(),
                    false
            );
            formList.add(staredFormParent);
        }

        if (!recentFormList.isEmpty()) {
            List<RecentForm> sortedRecent = recentFormList.stream()
                    .sorted(Comparator.comparing(RecentForm::getNumberOfVisit))
                    .sorted(Comparator.comparing(RecentForm::getVisitedTime).reversed())
                    .collect(Collectors.toList());

            Form recentFormParent = new Form(
                    1000,
                    "1000",
                    new Nls("Последние формы", "Сунги формалар", "So'ngi formalar", "Recent forms", "So'ngi formalar"),
                    new Status("A", new Nls("Активный", "Фаол", "Faol", "Active", "Faol")),
                    1000,
                    null,
                    null,
                    new Nls("Последние формы", "Сунги формалар", "So'ngi formalar", "Recent forms", "So'ngi formalar").getActiveLanguage()

            );

            sortedRecent.forEach(recentForm -> recentForms.add(new Form(
                    recentForm.getForm().getId(),
                    null,
                    recentForm.getForm().getName(),
                    recentForm.getForm().getStatus(),
                    recentForm.getForm().getOrderNumber(),
                    recentFormParent,
                    new ArrayList<>(),
                    recentForm.getForm().getHrefAddress(),
                    recentForm.getForm().getFormActiveName(),
                    recentForm.getVisitedTime(),
                    recentForm.getForm().isStarred()
            )));

            recentFormParent.setChildForms(recentForms);

            formList.add(recentFormParent);
        }

        formList.forEach(form -> {
            form.setSearchKey();
            form.setParentFormId(form.getParentForm() != null ? form.getParentForm().getId() : null);
        });

        List<Form> sortedList = formList.stream()
                .sorted(Comparator.comparing(Form::isStarred).reversed())
                .collect(Collectors.toList());

        sortedList.removeIf(form -> form.getParentForm() != null);
        filterByUserPermission(sortedList, user);
        for(Form form : staredForms){
            form.setStarred(true);
        }
        return sortedList;
    }

    public void filterByUserPermission(List<Form> forms, @NotNull User user) {
        if (
                user.getSystemRoleName() == SystemRoleName.SYSTEM_ROLE_SUPER_ADMIN
                        || user.getSystemRoleName() == SystemRoleName.SYSTEM_ROLE_ADMIN
        ) {
            sortingFormsBasedOnSomeFactors(forms);
            return;
        }

        if (user.getSystemRoleName() == SystemRoleName.SYSTEM_ROLE_MAIN_AUDITOR || user.getSystemRoleName() == SystemRoleName.SYSTEM_ROLE_AUDITOR || user.getSystemRoleName() == SystemRoleName.SYSTEM_ROLE_MODERATOR) {
            forms.removeIf(f -> f.getId() == 130);
            sortingFormsBasedOnSomeFactors(forms);
            return;
        }
        removeByTraversingForms(forms, user);
    }

    private void removeByTraversingForms(@NotNull List<Form> forms, @NotNull User user) {
//        forms.sort(Comparator.comparing(Form::isStarred).reversed());
        forms.sort(Comparator.comparing(Form::getLastVisited).thenComparing(Form::getStarredAt).reversed().thenComparing(Form::getId));
        forms.removeIf(form -> {
            if (!form.getChildForms().isEmpty()) {
                removeByTraversingForms(form.getChildForms(), user);
            }
            boolean isAccess = false;

//            for (RoleFormPermission roleFormPermission : user.getRole().getRoleFormPermissions()) {
//                if (form.getId() == roleFormPermission.getForm().getId()) {
//                    isAccess = (roleFormPermission.isCanInsert() || roleFormPermission.isCanEdit()) && form.getStatus().getStatus().equals("A");
//                    break;
//                }
//            }

            if (!isAccess)
                return form.getChildForms().isEmpty();
            return false;
        });
    }

    private void sortingFormsBasedOnSomeFactors(List<Form> forms) {
        forms.sort(Comparator.comparing(Form::getLastVisited).thenComparing(Form::getStarredAt).reversed().thenComparing(Form::getId));
        forms.forEach(form -> {
            if (!form.getChildForms().isEmpty()) {
                sortingFormsBasedOnSomeFactors(form.getChildForms());
            }
        });
    }

    @Transactional
    public ApiResponse delete(int id) {
        Optional<Form> optionalForm = formRepository.findById(id);
        if (optionalForm.isEmpty()) {
            return new ApiResponse(false, id, LanguageManager.getLangMessage("cant_find"));
        }else{
            optionalForm.get().setStatus(statusRepository.getOne("P"));
            formRepository.save(optionalForm.get());
            return new ApiResponse(true, id, LanguageManager.getLangMessage("deleted"));
        }
    }

}
