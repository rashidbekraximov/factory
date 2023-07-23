package uz.cluster.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    private static final long expireTime = 3_600_000;
    private static final String keyword = "keyword";

    public String generateToken(String username/*, Role role*/){
        Date expireDate = new Date(System.currentTimeMillis() + expireTime);
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                /*.claim("role", role)*/
                .signWith(SignatureAlgorithm.HS512, keyword)
                .compact();
    }
}
