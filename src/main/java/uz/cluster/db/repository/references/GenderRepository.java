package uz.cluster.db.repository.references;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.cluster.db.entity.references.model.Gender;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {
}
