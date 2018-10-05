package be.nexios.project.controller;

import be.nexios.project.config.JwtUtil;
import be.nexios.project.domain.User;
import be.nexios.project.service.UserService;
import be.nexios.project.service.dto.UserRegistrationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/api/register")
    public Mono<String> register(@Valid @RequestBody UserRegistrationDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/api/login")
    public Mono<ResponseEntity<Object>> login(@Valid @RequestBody UserRegistrationDTO dto){
        return userService.findByUsername(dto.getUsername())
                .map(userDetails -> {
                    if (passwordEncoder.matches(dto.getPassword(), userDetails.getPassword())) {
                        return ResponseEntity
                                .ok()
                                .header("Authorization", "Bearer", jwtUtil.createToken((User) userDetails))
                                .build();
                    }
                    else{
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                    }

                }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
