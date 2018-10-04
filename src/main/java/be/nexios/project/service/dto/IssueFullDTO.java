package be.nexios.project.service.dto;

import be.nexios.project.domain.enumeration.Priority;
import be.nexios.project.domain.enumeration.Status;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class IssueFullDTO {

    String id;

    String title;

    String description;

    Status status;

    Priority priority;

    Instant creationDate;

    String reporterId;

    String reporterName;

    String assigneeId;

    String assigneeName;
}
