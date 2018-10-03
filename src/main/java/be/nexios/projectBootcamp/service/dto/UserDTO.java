package be.nexios.projectBootcamp.service.dto;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotNull;

public class UserDTO {
    @NotNull
    String id;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    Flux<String> projects;
}
