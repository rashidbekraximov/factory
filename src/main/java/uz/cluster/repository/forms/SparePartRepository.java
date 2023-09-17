package uz.cluster.repository.forms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.cluster.entity.forms.SparePart;

import java.util.List;

@Repository
public interface SparePartRepository extends JpaRepository<SparePart,Long> {

    @Query(value = "select * from forms.spare_part t where t.document_id=:document_id", nativeQuery = true)
    List<SparePart> getByDocumentId(@Param("document_id") long documentId);


    @Modifying
    @Query(value = "delete from forms.spare_part t where t.document_id = :document_id", nativeQuery = true)
    void deleteByDocumentId(@Param("document_id") long documentId);

}
