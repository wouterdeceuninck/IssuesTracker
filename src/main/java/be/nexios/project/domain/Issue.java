package be.nexios.project.domain;

import be.nexios.project.domain.enumeration.Priority;
import be.nexios.project.domain.enumeration.Status;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.Instant;

@Data
@Builder
public class Issue {

    @Id
    ObjectId id;

    String title;

    String description;

    Status status;

    Priority priority;

    Instant creationDate;

    @DBRef
    User reporter;

    @DBRef
    User assignee;
}
