package be.nexios.projectBootcamp;

import be.nexios.projectBootcamp.service.dto.ProjectDTO;
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
public class ProjectApplicationIntegrationTest {

    @Autowired
    public WebTestClient webTestClient;

    @Test
    public void createValidProjectShouldReturn201Test() {
        ProjectDTO body = ProjectDTO
                .builder()
                .id("5bb378bfd5313b2a6cb20a3e")
                .name("some name")
                .description("some description")
                .build();

        this.webTestClient.post()
                .uri("/api/project")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(body), ProjectDTO.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location")
                .expectBody().isEmpty();
    }


}
