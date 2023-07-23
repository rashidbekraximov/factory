package uz.cluster.db.services.form_services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.cluster.db.entity.auth.User;
import uz.cluster.db.entity.forms.StaredForm;
import uz.cluster.db.entity.references.model.Form;
import uz.cluster.db.repository.forms.StaredFormRepository;
import uz.cluster.db.repository.references.FormRepository;
import uz.cluster.db.services.AbstractService;
import uz.cluster.mappers.StaredFormMapper;
import uz.cluster.payload.form.stared_forms.StaredFormCreateDTO;
import uz.cluster.payload.response.ApiResponse;
import uz.cluster.util.LanguageManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaredFormService implements AbstractService {
    private final StaredFormRepository staredFormRepository;
    private final StaredFormMapper mapper;
    private final FormRepository formRepository;

    public ResponseEntity<List<StaredForm>> getAllStaredForms(User user) {
        List<StaredForm> staredFormList = staredFormRepository.findAllByUserId(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(staredFormList);
    }

    public ResponseEntity<StaredForm> getStaredFormById(int formId, User user) {
        Optional<StaredForm> optionalStaredForm = staredFormRepository.findByFormIdAndUserId(formId, user.getId());
        if (optionalStaredForm.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        return ResponseEntity.status(HttpStatus.OK).body(optionalStaredForm.get());
    }

    public ResponseEntity<?> addStaredForm(StaredFormCreateDTO staredFormCreateDTO, User user) {
        Form starringForm = formRepository.findById(staredFormCreateDTO.getFormId()).orElse(null);
        if (starringForm != null && staredFormCreateDTO.isStar()){
            starringForm.setStarred(staredFormCreateDTO.isStar());
        }
        Optional<StaredForm> optionalStaredForm = staredFormRepository.findByFormIdAndUserId(staredFormCreateDTO.getFormId(), user.getId());
        if (optionalStaredForm.isPresent() && !staredFormCreateDTO.isStar()){
            Form unStaredForm = optionalStaredForm.get().getForm();
            staredFormRepository.delete(optionalStaredForm.get());
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(ApiResponse.builder()
                            .isSuccess(true)
                            .message(LanguageManager.getLangMessage("deleted"))
                            .object(unStaredForm)
                            .build()
                    );
        }
        if (optionalStaredForm.isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    ApiResponse.builder()
                            .isSuccess(false)
                            .message("already_exist")
                            .id(staredFormCreateDTO.getFormId())
            );

        if (starringForm == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiResponse.builder()
                            .isSuccess(false)
                            .message(LanguageManager.getLangMessage("no_data_submitted"))
                            .build()
            );

        StaredForm staredForm = StaredForm.builder()
                .form(starringForm)
                .user(user)
                .staredAt(LocalDateTime.now())
                .build();
        StaredForm savedStaredForm = staredFormRepository.save(staredForm);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.builder()
                                .isSuccess(true)
                                .message(LanguageManager.getLangMessage("saved"))
                                .object(mapper.toDTO(savedStaredForm))
                                .build()
                );
    }
}
