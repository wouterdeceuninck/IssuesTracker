package be.nexios.project.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectFullDTO {

    String id;

    String name;

    String description;

    List<IssueFullDTO> issues;
}