package uz.cluster.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.cluster.entity.auth.User;
import uz.cluster.services.auth_service.AuthService;

@Component
public class UserComponent {

    private static AuthService authService;

    @Autowired
    public UserComponent(AuthService authService) {
        UserComponent.authService = authService;
    }

    public static User getById(int id){
        return authService.getByUserIdComponent(id);
    }

}
