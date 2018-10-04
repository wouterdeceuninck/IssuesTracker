package be.nexios.project.service.impl;

import be.nexios.project.domain.Role;
import be.nexios.project.domain.User;
import be.nexios.project.repository.UserRepository;
import be.nexios.project.service.UserService;
import be.nexios.project.service.dto.UserRegistrationDTO;
import org.bson.types.ObjectId;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.function.Function;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<String> register(UserRegistrationDTO dto) {
        User user = new User();
        user.setId(ObjectId.get());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setAuthorities(Collections.singletonList(new Role("ROLE_USER")));
        user.setEnabled(true);
        return userRepository.save(user)
                .map(created -> created.getId().toHexString());
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username).map(Function.identity());
    }
}
