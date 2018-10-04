package be.nexios.project.controller;

import be.nexios.project.service.ProjectService;
import be.nexios.project.service.dto.ProjectDTO;
import be.nexios.project.service.exception.BadRequestException;
import be.nexios.project.service.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/account")
    public Mono<Authentication> getAccount() {
        return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication);
    }

    /**
     * POST /api/projects
     * Create a new project
     *
     * @param projectDto
     * @return
     */
    @PostMapping("/api/projects")
    public Mono<ResponseEntity<Void>> createProject(@Valid @RequestBody ProjectDTO projectDto) {
        return projectService.createProject(projectDto)
                .map(id -> ResponseEntity
                        .created(URI.create("/api/projects/" + id))
                        .build());
    }

    /**
     * GET /api/projects/{id}
     *
     * @param id
     * @return
     */
    @GetMapping("/api/projects/{id}")
    public Mono<ProjectDTO> getProjectById(@PathVariable("id") String id) {
        return projectService.getProject(id);
    }

    /**
     * GET /api/projects
     * Get a list of all the projects
     */
    @GetMapping("/api/projects")
    public Flux<ProjectDTO> getAllProjects() {
        return projectService.getProjects();
    }

    /**
     * PUT /api/projects/{id}
     * Update a project
     */
    @PutMapping("/api/projects/{id}")
    public Mono<Void> updateProject(@PathVariable("id") String id, @Valid @RequestBody ProjectDTO dto) {
        return projectService.updateProject(id, dto);
    }

    /**
     * DELETE /api/projects/{id}
     * Delete a project
     */
    @DeleteMapping("/api/projects/{id}")
    public Mono<Void> deleteProject(@PathVariable("id") String id) {
        return projectService.deleteProject(id);
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