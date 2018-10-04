package be.nexios.project.controller;

import be.nexios.project.service.UserService;
import be.nexios.project.service.dto.UserRegistrationDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/register")
    public Mono<String> register(@Valid @RequestBody UserRegistrationDTO dto) {
        return userService.register(dto);
    }
}
