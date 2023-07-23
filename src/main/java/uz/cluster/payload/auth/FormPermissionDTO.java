package uz.cluster.payload.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormPermissionDTO {
    private String  formNumber;
    private boolean canView;
    private boolean canInsert;
    private boolean canEdit;
    private boolean canDelete;
    private Integer time;

}
