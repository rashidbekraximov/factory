package uz.cluster.services.auth_service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.cluster.annotation.CheckPermission;
import uz.cluster.entity.auth.RoleFormPermission;
import uz.cluster.entity.references.model.Role;
import uz.cluster.repository.references.FormRepository;
import uz.cluster.repository.user_info.RoleRepository;
import uz.cluster.enums.auth.Action;
import uz.cluster.enums.forms.FormEnum;
import uz.cluster.payload.auth.FormPermissionDTO;
import uz.cluster.payload.auth.RoleDTO;
import uz.cluster.payload.response.ApiResponse;
import uz.cluster.util.GlobalParams;
import uz.cluster.util.LanguageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final FormRepository formRepository;

    @CheckPermission(form = FormEnum.ROlE_LIST, permission = Action.CAN_VIEW)
    public List<Role> getAll() {
        return roleRepository.findByClusterId(GlobalParams.getCurrentClusterId());
    }

    public Role getById(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role getByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

    @CheckPermission(form = FormEnum.ROlE_LIST, permission = Action.CAN_ADD)
    @Transactional
    public ApiResponse add(Role role) {
        if (role.getName() == null || role.getName().isEmpty())
            return new ApiResponse(false, role, LanguageManager.getLangMessage("no_data_submitted"));
        roleRepository.save(role);
        return new ApiResponse(true, role, LanguageManager.getLangMessage("saved"));
    }

    @Transactional
    public ApiResponse addWithPermission(RoleDTO roleDTO) {
        if (roleDTO.getName() == null || roleDTO.getName().isEmpty())
            return new ApiResponse(false, LanguageManager.getLangMessage("no_data_submitted"));

        if (roleRepository.existsByName(roleDTO.getName()))
            return new ApiResponse(false, LanguageManager.getLangMessage("already_exist"));

        Role role = new Role(roleDTO.getName(), roleDTO.isActive(), roleDTO.getDescription());

        List<RoleFormPermission> roleFormPermissionList = new ArrayList<>();
        int permissionCode = 0;

        for (FormPermissionDTO formPermissionDTO : roleDTO.getFormPermissionDTOS()) {
            if (formPermissionDTO.isCanView())
                permissionCode += 1000;
            if (formPermissionDTO.isCanInsert())
                permissionCode += 100;
            if (formPermissionDTO.isCanEdit())
                permissionCode += 10;
            if (formPermissionDTO.isCanDelete())
                permissionCode += 1;
            roleFormPermissionList.add(new RoleFormPermission(
                    role,
                    formRepository.findByFormNumber(formPermissionDTO.getFormNumber()).orElse(null),
                    permissionCode,
                    formPermissionDTO.isCanView(),
                    formPermissionDTO.isCanInsert(),
                    formPermissionDTO.isCanEdit(),
                    formPermissionDTO.isCanDelete(),
                    formPermissionDTO.getTime()
            ));
            permissionCode = 0;
        }

        role.setRoleFormPermissions(roleFormPermissionList);
        Role savedRole = roleRepository.save(role);
        return new ApiResponse(true, savedRole.getId(), LanguageManager.getLangMessage("saved"));
    }

    @Transactional
    public ApiResponse editWithPermission(RoleDTO roleDTO, int id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isEmpty())
            return new ApiResponse(false, LanguageManager.getLangMessage("cant_find"));

        if (roleRepository.existsByNameAndIdNot(roleDTO.getName(), id))
            return new ApiResponse(false, LanguageManager.getLangMessage("already_exist"));

        Role editingRole = optionalRole.get();
        editingRole.setName(roleDTO.getName());
        editingRole.setActive(roleDTO.isActive());
        editingRole.setDescription(roleDTO.getDescription());

        List<RoleFormPermission> roleFormPermissionList = new ArrayList<>();
        int permissionCode = 0;

        int i = 0;
        for (RoleFormPermission roleFormPermission : editingRole.getRoleFormPermissions()) {
            if (roleDTO.getFormPermissionDTOS().get(i).isCanView())
                permissionCode += 1000;
            if (roleDTO.getFormPermissionDTOS().get(i).isCanInsert())
                permissionCode += 100;
            if (roleDTO.getFormPermissionDTOS().get(i).isCanEdit())
                permissionCode += 10;
            if (roleDTO.getFormPermissionDTOS().get(i).isCanDelete())
                permissionCode += 1;
            roleFormPermission.setRole(editingRole);
            roleFormPermission.setForm(formRepository.findByFormNumber(roleDTO.getFormPermissionDTOS().get(i).getFormNumber()).orElse(null));
            roleFormPermission.setPermissionCode(permissionCode);
            roleFormPermission.setCanView(roleDTO.getFormPermissionDTOS().get(i).isCanView());
            roleFormPermission.setCanInsert(roleDTO.getFormPermissionDTOS().get(i).isCanInsert());
            roleFormPermission.setCanEdit(roleDTO.getFormPermissionDTOS().get(i).isCanEdit());
            roleFormPermission.setCanDelete(roleDTO.getFormPermissionDTOS().get(i).isCanDelete());
            roleFormPermission.setTime(roleDTO.getFormPermissionDTOS().get(i).getTime());
            permissionCode = 0;
            i++;
        }

        editingRole.setRoleFormPermissions(roleFormPermissionList);
        roleRepository.save(editingRole);
        return new ApiResponse(true, id, LanguageManager.getLangMessage("edited"));
    }

    @CheckPermission(form = FormEnum.ROlE_LIST, permission = Action.CAN_EDIT)
    @Transactional
    public ApiResponse edit(Role role, int id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isEmpty())
            return new ApiResponse(false, role, LanguageManager.getLangMessage("cant_find"));
        Role editingRole = optionalRole.get();
        editingRole.setName(role.getName());
        editingRole.setActive(role.isActive());
        editingRole.setDescription(role.getDescription());
        roleRepository.save(editingRole);
        return new ApiResponse(true, editingRole, LanguageManager.getLangMessage("saved"));
    }

    @CheckPermission(form = FormEnum.ROlE_LIST, permission = Action.CAN_DELETE)
    public ApiResponse delete(int id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isEmpty())
            return new ApiResponse(false, LanguageManager.getLangMessage("cant_find"));
        roleRepository.delete(optionalRole.get());
        return new ApiResponse(true, LanguageManager.getLangMessage("deleted"));
    }
}
