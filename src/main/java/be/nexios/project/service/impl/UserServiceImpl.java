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
import be.nexios.project.service.dto.UserDTO;
import be.nexios.project.service.dto.UserRegistrationDTO;
import org.bson.types.ObjectId;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        User user = User.builder()
            .id(ObjectId.get())
            .username(dto.getUsername())
            .password(passwordEncoder.encode(dto.getPassword()))
            .projects(new ArrayList<>())
            .authorities(Collections.singletonList(new Role("ROLE_USER")))
            .enabled(true)
            .build();
        return userRepository.save(user)
                .map(created -> created.getId().toHexString());
    }

    //new ObjectId(hexString)

    @Override
    public Mono<Void> addProject(ObjectId objectId, AddUserDTO addUserDTO) {
        return ReactiveSecurityContextHolder.getContext().flatMap(auth ->
            this.projectService.getProject(objectId.toHexString())
                    .flatMap(projectDTO ->
                        this.getUser(new ObjectId(addUserDTO.getId()))
                                .flatMap(user -> {
                                    user.getProjects().add(ProjectServiceImpl.toDomain(projectDTO));
                                    return this.userRepository.save(user).then();
                                })
                    )
        );
    }

    private Mono<User> getUser(ObjectId userId) {

        return this.userRepository.findById(userId);
    }


    public Mono<UserDTO> getUserDTO(ObjectId userId) {
        return this.getUser(userId)
                .map(UserServiceImpl::toDTO);
    }

//    private Project toDomain(User existing) {
//    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username).map(Function.identity());
    }

    public static User toUser(UserDTO dto){
        if (dto == null){
            return null;
        }
        return User.builder()
                .id(dto.getId() != null ? new ObjectId(dto.getId()) : null)
                .firstName(dto.getFirstname())
                .lastName(dto.getLastname())
                .projects(dto.getProjects() == null ?
                        new ArrayList<Project>() :
                        dto.getProjects().stream().map(ProjectServiceImpl::toDomain).collect(Collectors.toList()))
                .username(dto.getUsername())
                .authorities(dto.getAuthorities())
                .build();
    }

    public static UserDTO toDTO (User user){
        if (user == null){
            return null;
        }
        return UserDTO.builder()
                .id(user.getId().toHexString())
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .username(user.getUsername())
                .authorities(user.getAuthorities())
                .build();
    }
}
