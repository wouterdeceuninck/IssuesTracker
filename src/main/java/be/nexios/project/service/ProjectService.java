package be.nexios.project.service;

import be.nexios.project.domain.User;
import be.nexios.project.service.dto.IssueDTO;
import be.nexios.project.service.dto.IssueFullDTO;
import be.nexios.project.service.dto.ProjectDTO;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectService {

    /**
     * Create a new project
     *
     * @param dto A DTO of the project to create
     * @return A Mono of the UUID as a String
     */
//    Mono<String> createProject(ProjectDTO dto);

    Mono<ProjectDTO> getProject(String id);

    Flux<ProjectDTO> getProjects();

    Flux<ProjectDTO> getProjects(ObjectId userId);

    Mono<Void> updateProject(String id, ProjectDTO dto);

    Mono<Void> deleteProject(String id);

    Mono<String> createIssue(String projectId, IssueDTO dto);

    Flux<IssueDTO> getProjectIssues(String projectId);

    Mono<IssueFullDTO> getProjectIssue(String projectId, String issueId);
}
