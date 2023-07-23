package uz.cluster.payload.form.recent_forms;

import lombok.*;
import uz.cluster.payload.Dto;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "builder")
public class RecentFormCreateDTO implements Dto {
    @NotNull(message = "Form id cannot be null")
    private int formId;

    private String formName;
}
