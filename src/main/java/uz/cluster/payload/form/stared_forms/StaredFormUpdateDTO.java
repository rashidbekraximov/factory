package uz.cluster.payload.form.stared_forms;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import uz.cluster.payload.GenericDto;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class StaredFormUpdateDTO extends GenericDto {
    private int userId;
    private int formId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime staredAt;

    @Builder(builderClassName = "childBuilder")
    public StaredFormUpdateDTO(int id, int userId, int formId) {
        super(id);
        this.userId = userId;
        this.formId = userId;
    }
}
