package be.nexios.project.Mappers;

import be.nexios.project.domain.Project;
import be.nexios.project.service.dto.ProjectDTO;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;

public final class ProjectMapper {

    @NotNull
    public static ProjectDTO ProjectToDTONoId(Project project) {
        return ProjectDTO.builder()
                .creator(project.getCreator())
                .description(project.getDescription())
                .name(project.getName())
                .build();
    }

    @NotNull
    public static Project DTOToProjectNoId(ProjectDTO projectDTO) {
        return Project.builder()
                .creator(projectDTO.getCreator())
                .description(projectDTO.getDescription())
                .name(projectDTO.getName())
                .build();
    }

    @NotNull
    public static ProjectDTO ProjectToDTO(Project project) {
        ProjectDTO dto = ProjectMapper.ProjectToDTONoId(project);
        dto.setId(project.getId().toHexString());
        return dto;
    }

    @NotNull
    public static Project DTOToProject(ProjectDTO projectDTO) {
        Project project = ProjectMapper.DTOToProjectNoId(projectDTO);
        project.setId(new ObjectId(projectDTO.getId()));
        return project;
    }
}
