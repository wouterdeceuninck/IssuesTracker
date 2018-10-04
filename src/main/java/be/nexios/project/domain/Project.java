package be.nexios.project.domain;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@Document(collection = "projects")
public class Project {

    @Id
    ObjectId id;

    String name;

    String description;

    List<Issue> issues;

    @DBRef
    User creator;

    Instant creationDate;
}
