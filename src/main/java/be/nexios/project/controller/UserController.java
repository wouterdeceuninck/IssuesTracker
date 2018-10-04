package be.nexios.project.controller;

import be.nexios.project.domain.Project;
import be.nexios.project.service.ProjectService;
import be.nexios.project.service.UserService;
import be.nexios.project.service.dto.AddUserDTO;
import be.nexios.project.service.dto.ProjectDTO;
import be.nexios.project.service.dto.UserRegistrationDTO;
import org.bson.types.ObjectId;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;
    private final ProjectService projectService;

    public UserController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
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


}
