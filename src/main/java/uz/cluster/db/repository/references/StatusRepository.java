package uz.cluster.db.repository.references;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.cluster.db.entity.references.model.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, String> {
}
