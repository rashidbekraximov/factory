package uz.cluster.dao;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUserDto {
    private String userName;
    private String password;
}
