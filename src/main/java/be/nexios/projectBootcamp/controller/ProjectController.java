package be.nexios.projectBootcamp.controller;

import be.nexios.projectBootcamp.domain.Project;
import be.nexios.projectBootcamp.exception.BadRequestException;
import be.nexios.projectBootcamp.exception.NotFoundException;
import be.nexios.projectBootcamp.repository.ProjectRepository;
import be.nexios.projectBootcamp.service.ProjectService;
import be.nexios.projectBootcamp.service.dto.ProjectDTO;
import be.nexios.projectBootcamp.service.impl.ProjectServiceImpl;
import lombok.Data;
import lombok.Value;
import org.bson.types.ObjectId;
import org.reactivestreams.Publisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@RestController //Spring knows to map the object to JSON
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * POST /api/project
     * @param projectDTO
     * @return
     */
    @PostMapping("/api/project") //Handles post-requests for /api/project/ calls
    public Mono<ResponseEntity<Void>> createProject(@Valid @RequestBody ProjectDTO projectDTO) { // @RequestBody gets the request body of Rest call
        return projectService.createProject(projectDTO)
                .map(id -> ResponseEntity
                        .created(URI.create("/api/projects" + id))
                        .build());
    }

    /**
     * GET /api/project/{id}
     * @param id
     * @return
     */
    @GetMapping("/api/project/{id}")
    public Mono<ProjectDTO> getProjectById(@PathVariable("id") ObjectId id) {
        return projectService.getProject(id);
    }

    /**
     * GET /api/project
     * get a list of all the projects
     */
    @GetMapping("/api/project/")
    public Flux<ProjectDTO> getAllProjects() {
        return projectService.getAllProjects();
    }

    /**
     * PUT /api/project/{id}
     * Update a project
     */
    @PutMapping("/api/project/{id}")
    public Mono<Void> putProject(@PathVariable("id") ObjectId id, @Valid @RequestBody ProjectDTO projectDTO) {
        return projectService.putProject(projectDTO, id);
    }

    /**
     * DELETE /api/project/{id}
     * delete a project
     */
    @DeleteMapping("/api/project/{id}")
    public Mono<Void> deleteProject(@PathVariable("id") ObjectId id) {
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
