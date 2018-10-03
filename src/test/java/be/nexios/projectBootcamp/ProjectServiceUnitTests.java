package be.nexios.projectBootcamp;

import be.nexios.projectBootcamp.domain.Project;
import be.nexios.projectBootcamp.exception.BadRequestException;
import be.nexios.projectBootcamp.repository.ProjectRepository;
import be.nexios.projectBootcamp.service.dto.ProjectDTO;
import be.nexios.projectBootcamp.service.impl.ProjectServiceImpl;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectServiceUnitTests {
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.projectService = new ProjectServiceImpl(projectRepository);
    }

    @Test
    public void updateProjectsWithNonMatchingIdsShouldFail() {
        String hex = "5bb378bfd5313b2a6cb20a3e";
        ObjectId id = new ObjectId(hex);
        Mockito.when(projectRepository.findById(id))
                .thenReturn(Mono.just(Project.builder().id(id).description("qdf").build()));
//
        StepVerifier
                .create(projectService.putProject(
                        ProjectDTO.builder().id("5bb378bfd5313b2a6cb20a3a")
                                .name("qsdf").description("dsqf").build(), id))
                .expectError(BadRequestException.class)
                .verify();

    }

}
