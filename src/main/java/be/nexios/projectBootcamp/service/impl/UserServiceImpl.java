package be.nexios.projectBootcamp.service.impl;

import be.nexios.projectBootcamp.domain.Project;
import be.nexios.projectBootcamp.domain.User;
import be.nexios.projectBootcamp.exception.BadRequestException;
import be.nexios.projectBootcamp.exception.NotFoundException;
import be.nexios.projectBootcamp.repository.UserRepository;
import be.nexios.projectBootcamp.service.ProjectService;
import be.nexios.projectBootcamp.service.UserService;
import be.nexios.projectBootcamp.service.dto.ProjectDTO;
import be.nexios.projectBootcamp.service.dto.UserDTO;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final String userNotFoundErrorMsg(ObjectId id) {
        return "The user with ID {"+id+"} does not exist";
    }

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static User toUserWithRandomId(UserDTO userDTO) {
        return User.builder()
                .id(new ObjectId())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .projects(
                        userDTO.getProjects().stream().map(dto ->
                            ProjectServiceImpl.toProject(dto))
                        .collect(Collectors.toList())
                )
                .build();
    }

    public static User toUser(UserDTO userDTO) {
        User user = toUserWithRandomId(userDTO);
        user.setId(new ObjectId(userDTO.getId()));
        return user;
    }

    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .projects(
                        user.getProjects().stream().map(project ->
                                ProjectServiceImpl.toDTO(project))
                                .collect(Collectors.toList())
                )
                .id(user.getId().toHexString())
                .build();
    }

    @Override
    public Mono<ObjectId> createUser(UserDTO userDTO) {
        return userRepository.insert(toUserWithRandomId(userDTO))
                .map(User::getId);
    }

    @Override
    public Mono<UserDTO> getUser(ObjectId id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(this.userNotFoundErrorMsg(id))))
                .map(UserServiceImpl::toDTO);
    }

    @Override
    public Flux<UserDTO> getAllUsers() {
        return userRepository.findAll().map(UserServiceImpl::toDTO);
    }

    @Override
    public Mono<Void> updateUser(ObjectId id, UserDTO userDTO) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(this.userNotFoundErrorMsg(id))))
                .flatMap(existing -> {
                    if(!userDTO.getId().equals(id.toHexString()))
                        return Mono.error(new BadRequestException("Bad Request"));
                    return userRepository.save(toUser(userDTO)).then();
                });
    }

    @Override
    public Mono<Void> deleteUser(ObjectId id) {
        return userRepository.deleteById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("User with ID " + id + "does not exist")))
                .flatMap(existing -> userRepository.deleteById(id).then());
    }

    public Mono<Void> addProject(ProjectDTO projectDTO) {
        return userRepository.findById(new ObjectId(projectDTO.getCreator().getId()))
                .switchIfEmpty(Mono.error(new NotFoundException(this.userNotFoundErrorMsg(null))))
                .flatMap(existing -> {
                    existing.getProjects().add(ProjectServiceImpl.toProject(projectDTO));
                    return userRepository.save(existing).then();
                });
    }
}
