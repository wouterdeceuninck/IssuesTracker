package be.nexios.project.service.dto;

import be.nexios.project.domain.Role;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class UserDTO {

    String id;

    @NotNull
    String username;

    @NotNull
    String firstname;

    @NotNull
    String lastname;

    @NotNull
    List<Role> authorities;

}
