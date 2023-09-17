package uz.cluster.controllers.forms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.cluster.dao.response.DocumentResult;
import uz.cluster.entity.Document;
import uz.cluster.services.FormActions;
import uz.cluster.services.form_services.CheckTimePermission;
import uz.cluster.services.form_services.DocumentRepository;
import uz.cluster.util.IntegerUtil;
import uz.cluster.util.LanguageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormActionManager<T> {

    private final DocumentRepository documentRepository;

    private final CheckTimePermission checkTimePermission;

    @Transactional(readOnly = true)
    public void prepareDocument(Document document) throws Exception {

        if (document.getSeasonId()  == 0) {
            throw new Exception("sezon tanlanishi kerak");
        }
    }

   public DocumentResult addForm(FormActions<T> formActions, T formDao, HttpServletRequest request) {

        DocumentResult documentResult = new DocumentResult(IntegerUtil.getActiveFormId(request), request.getMethod(), 0, null);
        try {

            Document doc = ((Document) formDao);
            doc.setFormId(documentResult.getFormId());
            prepareDocument(doc);

            long docId = formActions.add(formDao);

            documentResult.setDocumentId(docId);
            documentResult.setMsg(LanguageManager.getLangMessage("saved"));
            return documentResult;
        } catch (Exception ex) {
            documentResult.setMsg(ex.getMessage());
            documentResult.setResponseCode(1);
        }

        return documentResult;
    }

   public DocumentResult save(FormActions<T> formActions, T formDao, HttpServletRequest request) {
//        checkTimePermission.checkTimePermission(documentRepository, ((Document) formDao).getDocumentId()); //This check that user has enough time to change data

        DocumentResult documentResult = new DocumentResult(IntegerUtil.getActiveFormId(request), request.getMethod(), 0, null);
        try {
            documentResult.setDocumentId(((Document) formDao).getDocumentId());
            {
                Optional<Document> oldDocument = documentRepository.findById(documentResult.getDocumentId());
                if (oldDocument.isEmpty()) {
                    documentResult.setMsg("eski dokument topilmadi!!");
                    documentResult.setResponseCode(1);
                    return documentResult;
                }
                Document doc = ((Document) formDao);
                doc.setUsdCourse(oldDocument.get().getUsdCourse());
                doc.setCreatedBy(oldDocument.get().getCreatedBy());
                doc.setCreatedOn(oldDocument.get().getCreatedOn());
                doc.setFormId(IntegerUtil.getActiveFormId(request));
                //    doc.setBrigadeId(oldDocument.get().getBrigadeId());

                prepareDocument(doc);
            }
            formActions.save(formDao);
            documentResult.setMsg(LanguageManager.getLangMessage("edited"));

            return documentResult;
        } catch (Exception ex) {
            documentResult.setMsg(ex.getMessage());
            documentResult.setResponseCode(1);
        //    TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
        }
        return documentResult;
    }

    public DocumentResult deleteByDocumentId(FormActions<T> formActions, long documentId, HttpServletRequest request) {
//        checkTimePermission.checkTimePermission(documentRepository, documentId); //This check that user has enough time to change data

        DocumentResult documentResult = new DocumentResult(IntegerUtil.getActiveFormId(request), "DELETE", 0, null);
        try {
            formActions.deleteByDocumentId(documentId);
            documentResult.setDocumentId(documentId);
            documentResult.setMsg(LanguageManager.getLangMessage("deleted"));
            return documentResult;
        } catch (Exception ex) {
            documentResult.setMsg(ex.getMessage());
            documentResult.setResponseCode(1);
        }

        return documentResult;
    }
}