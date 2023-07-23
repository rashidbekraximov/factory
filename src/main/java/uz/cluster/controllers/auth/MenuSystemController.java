package uz.cluster.controllers.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.cluster.db.entity.references.model.Form;
import uz.cluster.db.services.references_service.FormService;
import uz.cluster.util.GlobalParams;

import java.util.List;

@RestController
@RequestMapping("/api/menu-system")
@Tag(name = "Menu",description = "Menu")
public class MenuSystemController {

    private final FormService formService;

    @Autowired
    public MenuSystemController(FormService formService) {
        this.formService = formService;
    }

    @GetMapping
    private List<Form> getAll() {
            return formService.getAllFormLikeTree(GlobalParams.getCurrentUser());
    }
}
