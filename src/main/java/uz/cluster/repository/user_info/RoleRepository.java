package uz.cluster.repository.user_info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.cluster.entity.references.model.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
    Boolean existsByName(String name);
    Boolean existsByNameAndIdNot(String name, int id);

    List<Role> findByClusterId(int clusterId);
}
