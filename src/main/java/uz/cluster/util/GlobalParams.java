package uz.cluster.util;

import org.springframework.context.i18n.LocaleContextHolder;
import uz.cluster.component.UserComponent;
import uz.cluster.configuration.SpringSecurityAuditorAware;
import uz.cluster.entity.auth.User;
import uz.cluster.entity.references.model.Form;

import java.util.*;

public class GlobalParams {

    static private final String baseBundleName = "langs/message";

    public GlobalParams() {
    }

    public static ResourceBundle getBundle() {
        return ResourceBundle.getBundle(baseBundleName, LocaleContextHolder.getLocale());
    }

    private static double usdCourse = 11700;

    public static double getUsdCourse() {
        return usdCourse;
    }

    public static int getCurrentClusterId() {
        SpringSecurityAuditorAware s = new SpringSecurityAuditorAware();
        int activeUserId = 0;
        if (s.getCurrentAuditor().isPresent() && s.getCurrentAuditor().get() != -1){
            return UserComponent.getById(s.getCurrentAuditor().get()).getClusterId();
        }else{
            return activeUserId;
        }
    }

    public static User getCurrentUser() {
        SpringSecurityAuditorAware s = new SpringSecurityAuditorAware();
        if (s.getCurrentAuditor().isPresent() && s.getCurrentAuditor().get() != -1){
            return UserComponent.getById(s.getCurrentAuditor().get());
        }
        if (s.getCurrentUser().isPresent()){
            return s.getCurrentUser().get();
        }
        return new User();
    }
}
