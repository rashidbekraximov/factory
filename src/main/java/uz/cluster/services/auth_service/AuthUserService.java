package uz.cluster.services.auth_service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.cluster.repository.user_info.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthUserService implements AbstractService, UserDetailsService {

    private final UserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return authUserRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(login));
    }

}
