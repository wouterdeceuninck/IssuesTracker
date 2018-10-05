package be.nexios.project.config;

import be.nexios.project.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.bson.internal.Base64;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationManager implements ReactiveAuthenticationManager {

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        try {
            String token = authentication.getCredentials().toString();
            Jwts.parser().setSigningKey(Base64.encode("yoursecret".getBytes()))
                    .parseClaimsJws(token)
                    .getBody();

            Claims claims = Jwts.parser()
                    .setSigningKey(Base64.encode("yoursecret".getBytes()))
                    .parseClaimsJws(token)
                    .getBody();

            List<GrantedAuthority> authorities = new ArrayList<>();
            List fromToken = claims.get("authorities", List.class);
            for (Object o : fromToken) {
                authorities.add(Role.builder().authority(o.toString()).build());
            }

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(),
                    null,
                    authorities
            );
            return Mono.just(auth);
        }
        catch (Exception e){
            return Mono.empty();
        }
    }
}
