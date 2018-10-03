package be.nexios.projectBootcamp.service.impl;

import be.nexios.projectBootcamp.domain.Project;
import be.nexios.projectBootcamp.exception.BadRequestException;
import be.nexios.projectBootcamp.exception.NotFoundException;
import be.nexios.projectBootcamp.repository.ProjectRepository;
import be.nexios.projectBootcamp.service.ProjectService;
import be.nexios.projectBootcamp.service.dto.ProjectDTO;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public static Project toProjectWithRandomId(ProjectDTO projectDTO) {
        return Project.builder()
                .id(new ObjectId())
                .name(projectDTO.getName())
                .description(projectDTO.getDescription())
                .build();
    }

    public static Project toProject( ProjectDTO dto ) {
        Project p = toProjectWithRandomId(dto);
        p.setId(new ObjectId(dto.getId()));
        return p;
    }

    public static ProjectDTO toDTO( Project p ) {
        return ProjectDTO.builder()
                .id(p.getId().toHexString())
                .name(p.getName())
                .description(p.getDescription())
                .build();
    }

    @Override
    public Mono<ObjectId> createProject(ProjectDTO projectDTO) {
//        Project p = toProject(projectDTO);
//        p.setId(new ObjectId());
        return projectRepository.insert(toProjectWithRandomId(projectDTO))
                .map( Project::getId );

    }

    @Override
    public Mono<ProjectDTO> getProject(ObjectId id) {
        return projectRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Project with ID {"+id+"} does not exist")))
                .map(ProjectServiceImpl::toDTO);
    }

    @Override
    public Flux<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().map(ProjectServiceImpl::toDTO);
    }

    @Override
    public Mono<Void> putProject(ProjectDTO projectDTO, ObjectId id) {
        //TODO Compare ids ( bad id )
        return projectRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Project with ID {"+id+"} does not exist")))
                .flatMap(existing -> {
                    if (!projectDTO.getId().equals(id.toHexString()))
                        return Mono.error(new BadRequestException("Bad Request"));
                    return projectRepository.save(toProject(projectDTO)).then();
                });
    }

    @Override
    public Mono<Void> deleteProject(ObjectId id) {
        //TODO check if project exists: projectRepository.existsById(id)
        return projectRepository.deleteById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Project with ID {"+id+"} does not exist")))
                .flatMap(existing -> projectRepository.deleteById(id).then());
    }
}
