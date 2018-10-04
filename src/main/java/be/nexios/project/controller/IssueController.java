package be.nexios.project.controller;

import be.nexios.project.service.ProjectService;
import be.nexios.project.service.dto.IssueDTO;
import be.nexios.project.service.dto.IssueFullDTO;
import be.nexios.project.service.exception.BadRequestException;
import be.nexios.project.service.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class IssueController {

    private final ProjectService projectService;

    public IssueController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/api/projects/{projectId}/issues")
    public Mono<ResponseEntity<Void>> createProject(@PathVariable("projectId") String projectId, @Valid @RequestBody IssueDTO issueDTO) {
        return projectService.createIssue(projectId, issueDTO)
                .map(id -> ResponseEntity
                        .created(URI.create("/api/projects/" + projectId + "/issues/" + id))
                        .build());
    }

    @GetMapping("/api/projects/{projectId}/issues")
    public Flux<IssueDTO> getProjectIssues(@PathVariable("projectId") String projectId) {
        return projectService.getProjectIssues(projectId);
    }

    @GetMapping("/api/projects/{projectId}/issues/{id}")
    public Mono<IssueFullDTO> getProjectIssue(@PathVariable("projectId") String projectId, @PathVariable("id") String id) {
        return projectService.getProjectIssue(projectId, id);
    }

    @ExceptionHandler
    public ResponseEntity handleNotFoundException(NotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler
    public ResponseEntity handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest().build();
    }
}
