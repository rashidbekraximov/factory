package uz.cluster.annotation;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CheckPermissionExecutor {

//    @Before(value = "@annotation(checkPermission)")
//    public void checkPermission(CheckPermission checkPermission) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        FormEnum form = checkPermission.form();
//        Action permission = checkPermission.permission();
//        boolean exist = false;
//
//        switch (user.getSystemRoleName()) {
//            case SYSTEM_ROLE_SUPER_ADMIN: {
//                return;
//            }
//            case SYSTEM_ROLE_ADMIN: {
//                if (form == FormEnum.ADMIN_PANEL_ADD_ADMIN)
//                    throw new ForbiddenException("Admin", "No such permission");
//                return;
//            }
//            case SYSTEM_ROLE_MAIN_AUDITOR: {
//                if (form == FormEnum.ADMIN_PANEL)
//                    throw new ForbiddenException("Moderator", "No such permission");
//                if (permission == Action.CAN_ADD || permission == Action.CAN_EDIT || permission == Action.CAN_DELETE)
//                    throw new ForbiddenException("Moderator", "No such permission");
//                return;
//            }
//            case SYSTEM_ROLE_AUDITOR: {
//                if (form == FormEnum.ADMIN_PANEL)
//                    throw new ForbiddenException("Moderator", "No such permission");
//
//                if (permission == Action.CAN_EDIT || permission == Action.CAN_ADD || permission == Action.CAN_DELETE)
//                    throw new ForbiddenException("Moderator", "No such permission");
//                return;
//            }
//            case SYSTEM_ROLE_MODERATOR: {
//                if (form == FormEnum.ADMIN_PANEL)
//                    throw new ForbiddenException("Moderator", "No such permission");
//
//                if (form.getValue() > 110 && form.getValue() <= 123)
//                    return;
//                throw new ForbiddenException("Moderator", "No such permission");
//            }
//
//            case SYSTEM_ROLE_FORM_MEMBER:
//            case SYSTEM_ROLE_MEMBER:
//                if (user.getRole() == null || user.getRole().isActive()) {
//                    for (RoleFormPermission roleFormPermission : user.getRole().getRoleFormPermissions()) {
//                        if (permission.getValue() == 1000) {
//                            if (roleFormPermission.getForm().getId() == form.getValue() && roleFormPermission.isCanView()) {
//                                exist = true;
//                                break;
//                            }
//                        } else if (permission.getValue() == 100) {
//                            if (roleFormPermission.getForm().getId() == form.getValue() && roleFormPermission.isCanInsert()) {
//                                exist = true;
//                                break;
//                            }
//                        } else if (permission.getValue() == 10) {
//                            if (roleFormPermission.getForm().getId() == form.getValue() && roleFormPermission.isCanEdit()) {
//                                exist = true;
//                                break;
//                            }
//                        } else if (permission.getValue() == 1) {
//                            if (roleFormPermission.getForm().getId() == form.getValue() && roleFormPermission.isCanDelete()) {
//                                exist = true;
//                                break;
//                            }
//                        }
//                    }
//                } else
//                    throw new ForbiddenException("Role", "Role is not defined or not active in this user");
//                break;
//            default:
//                throw new ForbiddenException("SYSTEM_ROLE_NAME", "System role is not defined in this user");
//        }
//
//
//        if (!exist)
//            throw new ForbiddenException(checkPermission.form() + " => " + checkPermission.permission(), LanguageManager.getLangMessage("forbidden"));
//    }
}
