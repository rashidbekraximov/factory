package uz.cluster.db.repository.user_info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.cluster.db.entity.auth.RoleFormPermission;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoleFormPermissionRepository extends JpaRepository<RoleFormPermission, UUID> {

    List<RoleFormPermission> findByRoleId(int role_id);
}
