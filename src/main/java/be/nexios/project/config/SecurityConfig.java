package be.nexios.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private final ReactiveAuthenticationManager authenticationManager;
    private final ServerSecurityContextRepository serverSecurityContextRepository;

    public SecurityConfig(ReactiveAuthenticationManager authenticationManager, ServerSecurityContextRepository serverSecurityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.serverSecurityContextRepository = serverSecurityContextRepository;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authenticationManager(this.authenticationManager)
                .securityContextRepository(this.serverSecurityContextRepository)
                .authorizeExchange()
                // .pathMatchers(HttpMethod.DELETE, "/api/projects/**").authenticated()
                .pathMatchers("/api/project/**").permitAll()
                .pathMatchers("/api/projects/**").permitAll()
                .pathMatchers("/api/deleteeveryting").hasAuthority("CAN_DELETE_EVERYTHING")
                .pathMatchers("/api/admin/**").hasRole("ADMIN") // ROLE_ADMIN
                .pathMatchers("/api/account").authenticated()
                .pathMatchers("/api/register").permitAll()
                .pathMatchers("/api/login").permitAll()
                .anyExchange().denyAll()
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
