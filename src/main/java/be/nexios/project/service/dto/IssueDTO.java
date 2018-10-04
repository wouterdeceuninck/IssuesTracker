package be.nexios.project.service.dto;

import be.nexios.project.domain.enumeration.Priority;
import be.nexios.project.domain.enumeration.Status;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class IssueDTO {

    String id;

    @NotBlank
    @Size(min = 1, max = 255)
    String title;

    @NotBlank
    @Size(min = 1, max = 4096)
    String description;

    @NotNull
    Status status;

    @NotNull
    Priority priority;
}
