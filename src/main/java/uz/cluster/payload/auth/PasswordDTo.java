package uz.cluster.payload.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordDTo {
    private String oldPassword;
    private String newPassword;
}
