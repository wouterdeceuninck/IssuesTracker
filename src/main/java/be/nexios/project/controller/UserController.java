package be.nexios.project.controller;

import be.nexios.project.config.JwtUtil;
import be.nexios.project.domain.User;
import be.nexios.project.service.ProjectService;
import be.nexios.project.service.UserService;
import be.nexios.project.service.dto.AddUserDTO;
import be.nexios.project.service.dto.ProjectDTO;
import be.nexios.project.service.dto.UserRegistrationDTO;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,
                          JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder) {

        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/api/register")
    public Mono<String> register(@Valid @RequestBody UserRegistrationDTO dto) {
        return userService.register(dto);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/project/{id}/addUser")
    public Mono<Void> addProject(@PathVariable("id") ObjectId objectId, @Valid @RequestBody AddUserDTO addUserDTO) {
        return userService.addProject(objectId, addUserDTO);
    }

    @PostMapping("/api/login")
    public Mono<ResponseEntity<Object>> login(@Valid @RequestBody UserRegistrationDTO dto) {
        return userService.findByUsername(dto.getUsername())
                .map(userDetails -> {
                    if(passwordEncoder.matches(dto.getPassword(), userDetails.getPassword()))
                        return ResponseEntity.ok()
                                .header("Authorization", "Bearer " + jwtUtil.createToken((User)userDetails))
                                .build();
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
