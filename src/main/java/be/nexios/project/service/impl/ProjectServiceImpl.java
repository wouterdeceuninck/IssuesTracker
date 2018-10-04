package be.nexios.project.service.impl;

import be.nexios.project.domain.Issue;
import be.nexios.project.domain.Project;
import be.nexios.project.domain.User;
import be.nexios.project.repository.ProjectRepository;
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

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
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

    //TODO check if project is in user project list
    @PreAuthorize("isAuthenticated()")
    @Override
    public Mono<ProjectDTO> getProject(String id) {
        return ReactiveSecurityContextHolder.getContext().flatMap(auth ->
                projectRepository.findById(new ObjectId(id))
                    .switchIfEmpty(Mono.error(new NotFoundException("Project with id " + id + " does not exist")))
                    .map(ProjectServiceImpl::toDTO));
    }

    //TODO get all projects of user
    @PreAuthorize("isAuthenticated()")
    @Override
    public Flux<ProjectDTO> getProjects() {
        return ReactiveSecurityContextHolder.getContext().flux().flatMap( auth -> {
            User user = (User) auth.getAuthentication().getPrincipal();
            return projectRepository.findAllByCreatorId(user.getId())
                    .map(project -> ProjectServiceImpl.toDTO(project));
        });
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public Mono<Void> updateProject(String id, ProjectDTO dto) {
//        return ReactiveSecurityContextHolder.getContext().flatMap( auth ->
//                this.getProject(id)
//        )
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
