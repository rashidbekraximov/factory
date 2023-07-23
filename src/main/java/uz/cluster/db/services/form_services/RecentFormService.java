package uz.cluster.db.services.form_services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.cluster.db.entity.auth.User;
import uz.cluster.db.entity.forms.RecentForm;
import uz.cluster.db.entity.references.model.Form;
import uz.cluster.db.repository.forms.RecentFormRepository;
import uz.cluster.db.repository.references.FormRepository;
import uz.cluster.db.services.AbstractService;
import uz.cluster.payload.form.recent_forms.RecentFormCreateDTO;
import uz.cluster.payload.response.ApiResponse;
import uz.cluster.util.LanguageManager;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecentFormService implements AbstractService {
    private final RecentFormRepository recentFormRepository;
    private final FormRepository formRepository;

    public ResponseEntity<List<RecentForm>> getAllStaredForms(User user) {
        List<RecentForm> recentFormList = recentFormRepository.findAllByUserId(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(recentFormList);
    }

    public ResponseEntity<?> addStaredForm(RecentFormCreateDTO recentFormCreateDTO, User user) {
        Optional<RecentForm> optionalRecentForm = recentFormRepository.findByFormIdAndUserId(recentFormCreateDTO.getFormId(), user.getId());
        List<RecentForm> recentFormList = recentFormRepository.findAllByUserId(user.getId());
        Optional<Form> optionalForm = formRepository.findById(recentFormCreateDTO.getFormId());

        if (optionalForm.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.builder()
                            .message(LanguageManager.getLangMessage("cant_find"))
                            .isSuccess(false)
                            .id(recentFormCreateDTO.getFormId())
                            .build()
            );

        if (optionalRecentForm.isPresent()) {
            RecentForm recentForm = optionalRecentForm.get();
            int numberOfVisit = recentForm.getNumberOfVisit();
            recentForm.setNumberOfVisit(++numberOfVisit);
            recentForm.setVisitedTime(LocalDateTime.now());
            recentFormRepository.save(recentForm);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.builder()
                            .isSuccess(true)
                            .message(LanguageManager.getLangMessage("saved"))
                            .id(recentFormCreateDTO.getFormId())
            );

        }

        if (recentFormList.size() < 10) {
            RecentForm recentForm = RecentForm.builder()
                    .form(optionalForm.get())
                    .user(user)
                    .visitedTime(LocalDateTime.now())
                    .numberOfVisit(1)
                    .build();
            recentFormRepository.save(recentForm);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.builder()
                            .isSuccess(true)
                            .message(LanguageManager.getLangMessage("saved"))
                            .id(recentFormCreateDTO.getFormId())
                            .build()
            );
        } else {
            recentFormList.sort(Comparator.comparing(RecentForm::getVisitedTime));
            recentFormRepository.delete(recentFormList.get(0));

            recentFormRepository.save(
                    RecentForm.builder()
                            .form(optionalForm.get())
                            .user(user)
                            .visitedTime(LocalDateTime.now())
                            .numberOfVisit(1)
                            .build()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.builder()
                            .isSuccess(true)
                            .message(LanguageManager.getLangMessage("saved"))
                            .id(recentFormCreateDTO.getFormId())
                            .object(recentFormCreateDTO)
                            .build()
            );
        }

    }
}
