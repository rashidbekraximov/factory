package uz.cluster.repository.references;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.cluster.entity.references.model.MechanicalProduct;

@Repository
public interface MechanicalProductRepository extends JpaRepository<MechanicalProduct,Integer> {
}
