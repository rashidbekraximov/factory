package uz.cluster.services.form_services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.cluster.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Long> {

    @Query(value = "select * from documents t where t.id=:document_id", nativeQuery = true)
    Document getByDocumentId(@Param("document_id") long documentId);

}
