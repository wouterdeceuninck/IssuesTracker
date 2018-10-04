package be.nexios.project.service;

import be.nexios.project.service.dto.AddUserDTO;
import be.nexios.project.service.dto.ProjectDTO;
import be.nexios.project.service.dto.UserRegistrationDTO;
import org.bson.types.ObjectId;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

public interface UserService extends ReactiveUserDetailsService {

    Mono<String> register(UserRegistrationDTO dto);

    Mono<Void> addProject(ObjectId objectId, AddUserDTO addUserDTO);
}
