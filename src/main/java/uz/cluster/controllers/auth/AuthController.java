package uz.cluster.controllers.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uz.cluster.db.model.JwtResponse;
import uz.cluster.db.services.auth_service.AuthService;
import uz.cluster.payload.auth.LoginDTO;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController  {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public HttpEntity<?> login(@RequestBody @Valid LoginDTO loginDTO){
        JwtResponse jwtResponse = authService.login(loginDTO);
        return ResponseEntity.status(jwtResponse.isSuccess() ? 200 : 403).body(jwtResponse);
    }
}