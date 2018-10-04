package be.nexios.projectBootcamp.domain;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@Document
public class User {
    @Id
    ObjectId id;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    @DBRef
    List<Project> projects;
}
