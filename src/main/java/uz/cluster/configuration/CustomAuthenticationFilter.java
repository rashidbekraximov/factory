package uz.cluster.configuration;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.cluster.db.entity.auth.User;
import uz.cluster.db.model.SessionDto;
import uz.cluster.db.model.response.AppErrorDto;
import uz.cluster.db.model.response.DataDto;
import uz.cluster.payload.auth.LoginDTO;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginDTO loginDto = new ObjectMapper().readValue(request.getReader(), LoginDTO.class);
            log.info("Username is: {}", loginDto.getLogin());
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException, IOException {
        User user = (User) authentication.getPrincipal();
        Date expiryForAccessToken = JwtUtils.getExpiry();
        Date expiryForRefreshToken = JwtUtils.getExpiryForRefreshToken();
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiryForAccessToken)
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(JwtUtils.getAlgorithm());

        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiryForRefreshToken)
                .withIssuer(request.getRequestURL().toString())
                .sign(JwtUtils.getAlgorithm());

        SessionDto sessionDto = SessionDto.builder()
                .accessToken(accessToken)
                .accessTokenExpiry(expiryForAccessToken.getTime())
                .user(user)
                .refreshToken(refreshToken)
                .refreshTokenExpiry(expiryForRefreshToken.getTime())
                .issuedAt(System.currentTimeMillis())
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), new DataDto<>(sessionDto));

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        DataDto<AppErrorDto> resp = new DataDto<>(
                AppErrorDto.builder()
                        .message(failed.getMessage())
                        .path(request.getRequestURL().toString())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build()
        );
        new ObjectMapper().writeValue(response.getOutputStream(), resp);
    }
}