package be.nexios.project.repository;

import be.nexios.project.domain.Role;
import be.nexios.project.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, ObjectId> {

    Mono<User> findByUsername(String username);

    Flux<User> findAllByAuthoritiesContains(Role role);
}
