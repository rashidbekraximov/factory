package uz.cluster.util;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import uz.cluster.configuration.SpringSecurityAuditorAware;
import uz.cluster.db.entity.auth.User;
import uz.cluster.db.entity.references.model.Form;
import uz.cluster.db.entity.references.model.Language;
import uz.cluster.db.entity.references.model.Status;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

public class GlobalParams {
    //parametr sifatida kiritishni qilishimiz kerak
    static private final String baseBundleName = "langs/message";

    public GlobalParams() {
    }


    public static ResourceBundle getBundle() {
        return ResourceBundle.getBundle(baseBundleName, LocaleContextHolder.getLocale());
    }

    private static final double CONST_SINGLE_VALUE_ADDED_TAX = 12;// yagona qiymat solig'i    ish xaqi uchun

    private static final double CONST_VALUE_ADDED_TAX = 15; // qo''shimcha qiymat solig'i ishlab chiqarish uchun
    public static final int AGE_RESTRICTION = 18; // Age restriction for workers who should not be less than this number


    private static List<Form> userForms = new ArrayList<>();
    private static List<Status> statuses = new ArrayList<>();
    private static List<Language> languages;
    private static List<Integer> pageCounts;

    public static List<Form> getUserForms() {
        return userForms;
    }

    public static List<Form> getUserFormsUi() {
        return userForms.stream().filter(form -> "A".equals(form.getStatus().getStatus())).collect(Collectors.toList());
    }

    public static void setUserForms(List<Form> userForms) {
        GlobalParams.userForms = userForms;
    }

    public static List<Language> getLanguages() {
        return languages;
    }

    public static int getCurrentClusterId() {
        SpringSecurityAuditorAware s = new SpringSecurityAuditorAware();
        int activeUserId = 0;
        if (s.getCurrentClusterId().isPresent()){
            activeUserId = s.getCurrentClusterId().get();
        }
        return activeUserId;
    }
    public static User getCurrentUser() {
        SpringSecurityAuditorAware s = new SpringSecurityAuditorAware();
        User activeUser = null;
        if (s.getCurrentUser().isPresent()){
            activeUser = s.getCurrentUser().get();
        }
        return activeUser;
    }
    public static void addToModel(Model model) {
        model.addAttribute("languages", languages);
    }

    public static List<Integer> getPageCounts() {
        return pageCounts;
    }

    public static List<Status> getStatus() {
        return statuses;
    }

    public String getActiveLanguageName() {
        Locale locale = LocaleContextHolder.getLocale();
        int languageId = Language.getLanguageId(locale.toString());
        Optional<Language> currentLanguage = languages.stream().filter(language -> language.getId() == languageId).findAny();
        if (currentLanguage.isPresent())
            return currentLanguage.get().getName();
        else
            return "";
    }

}
