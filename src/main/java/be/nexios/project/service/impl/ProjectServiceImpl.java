package be.nexios.project.service.impl;

import be.nexios.project.domain.Issue;
import be.nexios.project.domain.Project;
import be.nexios.project.domain.User;
import be.nexios.project.repository.ProjectRepository;
import be.nexios.project.repository.UserRepository;
import be.nexios.project.service.ProjectService;
import be.nexios.project.service.dto.IssueDTO;
import be.nexios.project.service.dto.IssueFullDTO;
import be.nexios.project.service.dto.ProjectDTO;
import be.nexios.project.service.exception.BadRequestException;
import be.nexios.project.service.exception.NotFoundException;
import org.bson.types.ObjectId;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.ArrayList;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public Mono<String> createProject(ProjectDTO dto) {
        return ReactiveSecurityContextHolder.getContext().flatMap(auth -> {
            Project project = toDomain(dto);
            project.setId(ObjectId.get());
            project.setIssues(new ArrayList<>());
            project.setCreator((User) auth.getAuthentication().getPrincipal());
            return projectRepository
                    .insert(project)
                    .map(created -> created.getId().toHexString());
        });
    }

    @Override
    public Mono<ProjectDTO> getProject(String id) {
        return projectRepository.findById(new ObjectId(id))
                .switchIfEmpty(Mono.error(new NotFoundException("Project with id " + id + " does not exist")))
                .map(ProjectServiceImpl::toDTO);
    }

    @Override
    public Flux<ProjectDTO> getProjects() {
        return projectRepository.findAll().map(ProjectServiceImpl::toDTO);
    }

    @Override
    public Mono<Void> updateProject(String id, ProjectDTO dto) {
        return projectRepository.findById(new ObjectId(id))
                .switchIfEmpty(Mono.error(new NotFoundException("Project with id " + id + " does not exist")))
                .flatMap(existing -> {
                    if (!id.equals(dto.getId())) {
                        return Mono.error(new BadRequestException("Ids don't match"));
                    }
                    return projectRepository.save(toDomain(dto)).then();
                });
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public Mono<Void> deleteProject(String id) {
        return projectRepository.findById(new ObjectId(id))
                .switchIfEmpty(Mono.error(new NotFoundException("Project with id " + id + " does not exist")))
                .flatMap(existing -> projectRepository.deleteById(new ObjectId(id)).then());
    }

    @Override
    public Mono<String> createIssue(String projectId, IssueDTO dto) {
        return projectRepository
                .findById(new ObjectId(projectId))
                .flatMap(found -> {
                    Issue newIssue = toDomain(dto);
                    newIssue.setId(ObjectId.get());
                    newIssue.setCreationDate(Instant.now());
                    found.getIssues().add(newIssue);
                    return projectRepository.save(found)
                            .then(Mono.just(newIssue.getId().toHexString()));
                });
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public Mono<Void> addIssueToProject(String id, IssueDTO dto) {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(auth -> userRepository.findByUsername(auth.getAuthentication().getPrincipal().toString()))
                .flatMap(user -> userRepository.existsByIdAndProjectsContains(user.getId(), Project.builder().id(new ObjectId(id)).build()))
                .flatMap(exists -> {
                    if (exists){
                       return projectRepository.findById(new ObjectId(id))
                                .switchIfEmpty(Mono.error(new NotFoundException("Project with id " + id + " does not exist!")))
                                .flatMap(project -> {
                                    Issue issue = toDomain(dto);
                                    project.getIssues().add(issue);
                                    return projectRepository.save(project).then();
                                });
                    }
                    else{
                        System.out.println("Dit is fout");
                        return Mono.empty();
                    }
                });
    }

    @Override
    public Flux<IssueDTO> getProjectIssues(String projectId) {
        return projectRepository
                .findById(new ObjectId(projectId))
                .flatMapMany(found -> Flux.fromStream(found.getIssues()
                        .stream()
                        .map(ProjectServiceImpl::toDTO)));
    }

    @Override
    public Mono<IssueFullDTO> getProjectIssue(String projectId, String issueId) {
        return projectRepository
                .findById(new ObjectId(projectId))
                .flatMap(found -> Mono.just(found.getIssues()
                        .stream()
                        .filter(i -> i.getId().equals(new ObjectId(issueId)))
                        .map(ProjectServiceImpl::toFullDTO).findFirst().get()));
    }

    public static Project toDomain(ProjectDTO dto) {

        if (dto == null) {
            return null;
        }

        return Project.builder()
                .id(dto.getId() != null ? new ObjectId(dto.getId()) : null)
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public static ProjectDTO toDTO(Project project) {

        if (project == null) {
            return null;
        }

        return ProjectDTO.builder()
                .id(project.getId().toHexString())
                .name(project.getName())
                .description(project.getDescription())
                .creator(project.getCreator())
                .build();
    }

    public static Issue toDomain(IssueDTO dto) {

        if (dto == null) {
            return null;
        }

        return Issue.builder()
                .id(dto.getId() != null ? new ObjectId(dto.getId()) : null)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .priority(dto.getPriority())
                .build();
    }

    public static IssueDTO toDTO(Issue issue) {

        if (issue == null) {
            return null;
        }

        return IssueDTO.builder()
                .id(issue.getId().toHexString())
                .title(issue.getTitle())
                .description(issue.getDescription())
                .priority(issue.getPriority())
                .status(issue.getStatus())
                .build();
    }

    public static IssueFullDTO toFullDTO(Issue issue) {

        if (issue == null) {
            return null;
        }

        return IssueFullDTO.builder()
                .id(issue.getId().toHexString())
                .title(issue.getTitle())
                .description(issue.getDescription())
                .priority(issue.getPriority())
                .status(issue.getStatus())
                .assigneeId(issue.getAssignee() != null ? issue.getAssignee().getId().toHexString() : null)
                .assigneeName(issue.getAssignee() != null ? issue.getAssignee().getUsername() : null)
                .reporterId(issue.getReporter() != null ? issue.getReporter().getId().toHexString() : null)
                .reporterName(issue.getReporter() != null ? issue.getReporter().getUsername() : null)
                .creationDate(issue.getCreationDate())
                .build();
    }
}
