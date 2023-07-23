package uz.cluster.payload.form.stared_forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.cluster.payload.Dto;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaredFormCreateDTO implements Dto {
    @NotNull(message = "Form id cannot be null")
    private int formId;

    private boolean isStar;
}
