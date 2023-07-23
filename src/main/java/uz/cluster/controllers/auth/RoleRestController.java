package uz.cluster.controllers.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.cluster.db.entity.references.model.Role;
import uz.cluster.db.repository.references.FormRepository;
import uz.cluster.db.services.auth_service.RoleService;
import uz.cluster.payload.auth.RoleDTO;
import uz.cluster.payload.response.ApiResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role-list")
public class RoleRestController {

    final RoleService roleService;

    final FormRepository formRepository;

    @GetMapping
    public HttpEntity<?> getAll() {
        List<Role> roleList = roleService.getAll();
        return ResponseEntity.ok(roleList);
    }

    @GetMapping("/role-edit/{role_id}")
    public HttpEntity<?> getById(@PathVariable(name = "role_id") int id) {
        Role role = roleService.getById(id);
        return ResponseEntity.status(role != null ? 201 : 404).body(role);
    }


    @PostMapping("/role/save")
    public HttpEntity<?> add(@RequestBody RoleDTO roleDTO) {
        ApiResponse apiResponse;
        try {
            apiResponse = roleService.addWithPermission(roleDTO);
        } catch (Exception e) {
            e.printStackTrace();
            apiResponse = new ApiResponse(false, e.getMessage());
        }

        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/role-edit")
    public HttpEntity<?> edit(@RequestBody RoleDTO roleDTO) {
        ApiResponse apiResponse;
        try {
            apiResponse = roleService.editWithPermission(roleDTO, roleDTO.getId());
        }catch (Exception e) {
            e.printStackTrace();
            apiResponse = new ApiResponse(false, e.getMessage());
        }

        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }
}
