package be.nexios.project.service.dto;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// TODO we can add the roles of the User to be added to the project
@Data
@Builder
public class AddUserDTO {
    @NotNull String id;

}
