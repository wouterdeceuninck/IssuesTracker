package be.nexios.project.service.impl;

import be.nexios.project.domain.Project;
import be.nexios.project.domain.Role;
import be.nexios.project.domain.User;
import be.nexios.project.repository.ProjectRepository;
import be.nexios.project.repository.UserRepository;
import be.nexios.project.service.ProjectService;
import be.nexios.project.service.UserService;
import be.nexios.project.service.dto.AddUserDTO;
import be.nexios.project.service.dto.ProjectDTO;
import be.nexios.project.service.dto.UserRegistrationDTO;
import org.bson.types.ObjectId;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.function.Function;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectService projectService;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, ProjectRepository projectRepository,
                           ProjectService projectService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.projectService = projectService;
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
    public Mono<Void> addProject(ObjectId objectId, AddUserDTO addUserDTO) {
        return ReactiveSecurityContextHolder.getContext().flatMap(auth -> {
            Mono<ProjectDTO> project = this.projectService.getProject(objectId.toHexString());
            User user = this.
            return project.flatMap( projectDTO -> {
                user.getProjects().add(ProjectServiceImpl.toDomain(projectDTO));
                return userRepository.save(user).then();
            });
        });
    }

//    public Mono<UserDTO> getProject(ObjectId userId) {
//        return this.userRepository.findById(userId)
//                .flatMap(user -> UserServiceImpl::to);
//    }

//    private Project toDomain(User existing) {
//    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username).map(Function.identity());
    }


}
