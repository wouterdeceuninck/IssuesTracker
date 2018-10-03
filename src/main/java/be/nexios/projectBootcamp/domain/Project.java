package be.nexios.projectBootcamp.domain;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Document
public class Project {
    @Id
    ObjectId id;
    String name;
    String description;
    @DBRef
    List<Issue> issues;
}
