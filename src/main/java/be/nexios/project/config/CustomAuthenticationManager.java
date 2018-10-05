package be.nexios.project.config;

import be.nexios.project.domain.Role;
import io.jsonwebtoken.*;
import org.bson.internal.Base64;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationManager implements ReactiveAuthenticationManager {

    public static final String ENCODE = Base64.encode("somesecret".getBytes());

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        try {
            String token = authentication.getCredentials().toString();

            Claims claims = Jwts.parser()
                    .setSigningKey(ENCODE)
                    .parseClaimsJws(token)
                    .getBody();

            List<GrantedAuthority> grantedAuthorities =
                    ((List<String>)claims.get("authorities", List.class))
                            .stream()
                            .map(o -> Role.builder().authority(o.toString()).build())
                            .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
              claims.getSubject(),
              null,
                grantedAuthorities
            );

            return Mono.just(authenticationToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Mono.empty();
    }
}
