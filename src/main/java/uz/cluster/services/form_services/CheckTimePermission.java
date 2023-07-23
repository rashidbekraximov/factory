package uz.cluster.services.form_services;

import org.springframework.stereotype.Service;

@Service
public class CheckTimePermission {
//    public void checkTimePermission(DocumentRepository documentRepository, long documentId){
//        Document document = documentRepository.findById(documentId).orElse(new Document());
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (
//                user.getSystemRoleName() == SystemRoleName.SYSTEM_ROLE_FORM_MEMBER
//                        || user.getSystemRoleName() == SystemRoleName.SYSTEM_ROLE_MEMBER
//        ) {
//            for (RoleFormPermission roleFormPermission : user.getRole().getRoleFormPermissions()) {
//                if (roleFormPermission.getForm().getId() == FormEnum.FORM_1.getValue()) {
//                    if (Duration.between(document.getModifiedOn(), LocalDateTime.now()).getSeconds() / 3600 > roleFormPermission.getTime())
//                        throw new ForbiddenException("Time out", "Bu ma'lumotni o'zgartira olmaysiz. Ma'lumot oxirgi marta  " + Duration.between(document.getModifiedOn(), LocalDateTime.now()).getSeconds() / 3600 + " soat oldin o'zgartirilgan.");
//                }
//            }
//        }
//    }
}
