package uz.cluster.db.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import uz.cluster.db.entity.auth.User;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionDto implements Serializable {
    private Long accessTokenExpiry;
    private Long refreshTokenExpiry;
    private Long issuedAt;
    private String accessToken;
    private String refreshToken;
    private User user;
}
