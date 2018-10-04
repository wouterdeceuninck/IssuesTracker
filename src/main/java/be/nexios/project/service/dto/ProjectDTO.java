package be.nexios.project.service.dto;

import be.nexios.project.domain.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class ProjectDTO {

    String id;

    @NotNull
    @Size(min = 1, max = 255)
    String name;


    @NotNull
    @Size(min = 1, max = 4096)
    String description;

    @DBRef
    User creator;
}