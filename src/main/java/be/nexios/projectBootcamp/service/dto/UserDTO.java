package be.nexios.projectBootcamp.service.dto;

import be.nexios.projectBootcamp.domain.Project;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class UserDTO {
    @NotNull
    String id;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    List<Project> projects;
}
