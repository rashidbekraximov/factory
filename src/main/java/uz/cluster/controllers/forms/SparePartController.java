package uz.cluster.controllers.forms;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.cluster.dao.form_dao.SparePartDao;
import uz.cluster.dao.response.DocumentResult;
import uz.cluster.services.form_services.SparePartService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "spare-part")
@Tag(name = "Spare Part", description = "Ehtiyot qism forma ustida amallar")
public class SparePartController {

    final SparePartService sparePartService;

    final FormActionManager<SparePartDao> actionManager;

    @Autowired
    public SparePartController(SparePartService sparePartService, FormActionManager<SparePartDao> actionManager) {
        this.sparePartService = sparePartService;
        this.actionManager = actionManager;
    }

    @Hidden
    @Operation(summary = "Extiyot qismlar ro'yxati")
    @GetMapping()
    public HttpEntity<?> getAll() {
        return new ResponseEntity<>(sparePartService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Extiyot qismlar ro'yxati")
    @GetMapping("/document/{documentId}")
    public HttpEntity<?> getAll(@PathVariable("documentId") long documentId) {
        return new ResponseEntity<>(sparePartService.getByDocumentId(documentId), HttpStatus.OK);
    }

    @Operation(summary = "Extiyot qismga kiritish")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public DocumentResult addForm(@RequestBody SparePartDao formOtherSpendingDao, HttpServletRequest request) {
        return actionManager.addForm(sparePartService, formOtherSpendingDao, request);
    }

    @Operation(summary = "Extiyot qismlar ning o'zgarishlarini saqlash")
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public DocumentResult save(@RequestBody SparePartDao formOtherSpendingDao, HttpServletRequest request) {
        return actionManager.save(sparePartService, formOtherSpendingDao, request);
    }

    @Operation(summary = "Extiyot qismlar ni documentId bo'yicha olib tashlash")
    @DeleteMapping("/docuement/{docuementId}")
    public DocumentResult deleteByDocumentId(@Param("documentId bo'yicha o'chirish") @PathVariable("docuementId") long documentId, HttpServletRequest request) {
        return actionManager.deleteByDocumentId(sparePartService, documentId, request);
    }
}
