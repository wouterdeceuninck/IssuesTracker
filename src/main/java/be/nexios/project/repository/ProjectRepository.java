package be.nexios.project.repository;

import be.nexios.project.domain.Project;
import be.nexios.project.service.dto.ProjectDTO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ProjectRepository extends ReactiveMongoRepository<Project, ObjectId> {

    Flux<Project> findAllByCreatorId(ObjectId creatorId);
}
