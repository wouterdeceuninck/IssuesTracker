package be.nexios.project.domain;

import lombok.Data;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;

@Data
public class Role implements GrantedAuthority {

    @NonNull
    String authority;
}
