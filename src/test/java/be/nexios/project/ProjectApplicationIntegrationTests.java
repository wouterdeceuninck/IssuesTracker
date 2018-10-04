package be.nexios.project;

import be.nexios.project.repository.ProjectRepository;
import be.nexios.project.service.dto.ProjectDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ProjectApplicationIntegrationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void createValidProjectShoudReturn201Test() {

        ProjectDTO body = ProjectDTO.builder()
                .name("A test project")
                .description("A test description").build();

        this.webTestClient.post()
                .uri("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(body), ProjectDTO.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location")
                .expectBody().isEmpty();
    }

    @Test
    public void createInvalidProjectShoudReturn400Test() {

        ProjectDTO body = ProjectDTO.builder()
                .name("")
                .description("A test description").build();

        this.webTestClient.post()
                .uri("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(body), ProjectDTO.class)
                .exchange()
                .expectStatus().isBadRequest();
    }
}
