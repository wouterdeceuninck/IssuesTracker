package be.nexios.project.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
public class Role implements GrantedAuthority {

    @NonNull
    String authority;
}
