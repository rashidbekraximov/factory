package uz.cluster.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenericDto implements Dto{
    @NotNull(message = "Id cannot be null")
    private int id;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
