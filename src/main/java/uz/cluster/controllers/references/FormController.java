package uz.cluster.controllers.references;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.cluster.dao.FormDao;
import uz.cluster.entity.references.model.Form;
import uz.cluster.repository.references.FormRepository;
import uz.cluster.services.references_service.FormService;
import uz.cluster.types.Nls;
import uz.cluster.payload.response.ApiResponse;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/references")
@Tag(name = "References Formalar", description = "Spravichniy tablitsalar ustida amallar")
public class FormController {

    private final FormRepository formRepository;

    private final FormService formService;

    @GetMapping(value = "/forms")
    public List<Form> listPOST() {
        return formRepository.findAll();
    }

    @GetMapping("/form-name/{id}")
    public ResponseEntity<?> getFormName(@PathVariable("id") int id){
        Optional<Form> optionalForm = formRepository.findByFormNumber(String.valueOf(id));
        return optionalForm.<ResponseEntity<?>>map(form -> ResponseEntity.status(HttpStatus.CREATED).body(form.getName())).orElseGet(() -> ResponseEntity.status(HttpStatus.CREATED).body(new Nls()));
    }
    @GetMapping("/form/{id}")
    public ResponseEntity<?> getById(@Parameter(description = "id") @PathVariable Integer id){
        Form form = formService.getFormById(id);
        if (form == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(form);
    }

    @PostMapping("/form/save")
    public ResponseEntity<?> add(@RequestBody FormDao form){
        ApiResponse apiResponse = formService.update(form);
        if (!apiResponse.isSuccess())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/form/delete/{id}")
    public ResponseEntity<?> delete(@Parameter(description = "id") @PathVariable int id){
        ApiResponse apiResponseDelete = formService.delete(id);
        return ResponseEntity.ok().body(apiResponseDelete);
    }
}
