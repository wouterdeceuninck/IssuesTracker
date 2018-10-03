package be.nexios.projectBootcamp.repository;

import be.nexios.projectBootcamp.domain.Project;
import be.nexios.projectBootcamp.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, ObjectId> {
}
