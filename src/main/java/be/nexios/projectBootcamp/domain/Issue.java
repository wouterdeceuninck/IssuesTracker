package be.nexios.projectBootcamp.domain;

import be.nexios.projectBootcamp.enumeration.Priority;
import be.nexios.projectBootcamp.enumeration.Status;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@Document
public class Issue {

    @Id
    @NotNull
    ObjectId id;
    @NotNull
    @Size(min=1,max=4096)
    String description;
    @NotNull
    Status status;
    @NotNull
    Priority priority;
    String assigneeId;
    @NotNull
    String creatorId;
    LocalDate finishedDate;
    @NotNull
    LocalDate creationTimestamp;
}
