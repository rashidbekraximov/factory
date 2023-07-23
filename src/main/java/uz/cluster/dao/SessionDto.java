package uz.cluster.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import uz.cluster.entity.auth.User;
import uz.cluster.security.JwtResponse;

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
    private JwtResponse user;
}
