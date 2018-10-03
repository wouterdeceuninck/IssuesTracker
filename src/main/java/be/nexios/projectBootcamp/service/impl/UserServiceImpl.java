package be.nexios.projectBootcamp.service.impl;

import be.nexios.projectBootcamp.service.UserService;
import be.nexios.projectBootcamp.service.dto.UserDTO;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UserServiceImpl implements UserService {
    @Override
    public Mono<ObjectId> createUser(UserDTO user) {
        return null;
    }

    @Override
    public Mono<UserDTO> getUser(ObjectId id) {
        return null;
    }

    @Override
    public Flux<UserDTO> getAllUsers() {
        return null;
    }

    @Override
    public Mono<Void> updateUser(ObjectId id, UserDTO user) {
        return null;
    }

    @Override
    public Mono<Void> deleteUser(ObjectId id) {
        return null;
    }
}
