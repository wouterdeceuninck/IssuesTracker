package be.nexios.project.service;

import be.nexios.project.service.dto.UserRegistrationDTO;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

public interface UserService extends ReactiveUserDetailsService {

    Mono<String> register(UserRegistrationDTO dto);
}
