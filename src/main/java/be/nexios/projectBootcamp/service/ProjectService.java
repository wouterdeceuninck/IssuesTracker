package be.nexios.projectBootcamp.service;

import be.nexios.projectBootcamp.domain.Project;
import be.nexios.projectBootcamp.service.dto.ProjectDTO;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectService {
    Mono<ObjectId> createProject(ProjectDTO projectDTO);
    Mono<ProjectDTO> getProject(ObjectId id);
    Flux<ProjectDTO> getAllProjects();
    Mono<Void> putProject(ProjectDTO projectDTO, ObjectId id);
    Mono<Void> deleteProject(ObjectId id);
}
