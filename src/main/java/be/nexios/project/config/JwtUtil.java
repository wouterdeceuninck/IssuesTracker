package be.nexios.project.config;

import be.nexios.project.domain.Role;
import be.nexios.project.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bson.internal.Base64;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    public String createToken(User user){

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", user.getAuthorities().stream().map(Role::getAuthority).toArray());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + Duration.ofHours(1).toMillis()))
                .signWith(SignatureAlgorithm.HS512, Base64.encode("yoursecret".getBytes()))
                .compact();
    }
}
