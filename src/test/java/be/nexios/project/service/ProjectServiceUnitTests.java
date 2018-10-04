package be.nexios.project.service;

import be.nexios.project.domain.Project;
import be.nexios.project.repository.ProjectRepository;
import be.nexios.project.service.dto.ProjectDTO;
import be.nexios.project.service.exception.BadRequestException;
import be.nexios.project.service.impl.ProjectServiceImpl;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ProjectServiceUnitTests {

    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.projectService = new ProjectServiceImpl(projectRepository);
    }

    @Test
    public void updateProjectsWithNonMatchingIdsShouldFail() {

        ObjectId id = ObjectId.get();

        Mockito.when(projectRepository.findById(id))
                .thenReturn(Mono.just(Project.builder().id(id).description("My description").build()));

        StepVerifier
                .create(projectService.updateProject(id.toHexString(), ProjectDTO.builder().id(ObjectId.get().toHexString()).build()))
                .expectError(BadRequestException.class)
                .verify();
    }
}
