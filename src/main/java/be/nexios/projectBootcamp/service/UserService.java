package be.nexios.projectBootcamp.service;

import be.nexios.projectBootcamp.service.dto.UserDTO;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<ObjectId> createUser(UserDTO user);
    Mono<UserDTO> getUser(ObjectId id);
    Flux<UserDTO> getAllUsers();
    Mono<Void> updateUser(ObjectId id, UserDTO user);
    Mono<Void> deleteUser(ObjectId id);
}
