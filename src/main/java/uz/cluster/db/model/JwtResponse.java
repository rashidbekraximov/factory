package uz.cluster.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.cluster.db.entity.references.model.Gender;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private boolean success;
    private String fullName;
    private String firstName;
    private String lastName;
    private String middleName;
    private String birthday;
    private String email;
    private String login;
    private String roleName;
    private int clusterId;
    private String note;

}
