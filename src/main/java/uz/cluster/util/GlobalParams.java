package uz.cluster.util;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import uz.cluster.configuration.SpringSecurityAuditorAware;
import uz.cluster.entity.auth.User;
import uz.cluster.entity.references.model.Form;

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


    private static List<Form> userForms = new ArrayList<>();
    private static List<Integer> pageCounts;

    public static List<Form> getUserForms() {
        return userForms;
    }

    public static void setUserForms(List<Form> userForms) {
        GlobalParams.userForms = userForms;
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
}
