package be.nexios.project.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegistrationDTO {

    @NotBlank
    String username;

    @NotBlank
    String password;
}
