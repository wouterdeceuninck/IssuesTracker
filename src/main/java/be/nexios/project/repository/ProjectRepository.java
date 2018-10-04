package be.nexios.project.repository;

import be.nexios.project.domain.Project;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProjectRepository extends ReactiveMongoRepository<Project, ObjectId> {
}
