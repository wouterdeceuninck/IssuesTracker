package be.nexios.project.service;

import be.nexios.project.service.dto.IssueDTO;
import be.nexios.project.service.dto.IssueFullDTO;
import be.nexios.project.service.dto.ProjectDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectService {

    /**
     * Create a new project
     *
     * @param dto A DTO of the project to create
     * @return A Mono of the UUID as a String
     */
    Mono<String> createProject(ProjectDTO dto);

    Mono<ProjectDTO> getProject(String id);

    Flux<ProjectDTO> getProjects();

    Mono<Void> updateProject(String id, ProjectDTO dto);

    Mono<Void> deleteProject(String id);

    Mono<String> createIssue(String projectId, IssueDTO dto);

    Flux<IssueDTO> getProjectIssues(String projectId);

    Mono<IssueFullDTO> getProjectIssue(String projectId, String issueId);

    Mono<Void> addIssueToProject(String id, IssueDTO dto);
}
