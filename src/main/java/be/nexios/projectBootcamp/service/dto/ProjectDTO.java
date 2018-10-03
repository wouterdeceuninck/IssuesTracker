package be.nexios.projectBootcamp.service.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.Constraint;
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
}
