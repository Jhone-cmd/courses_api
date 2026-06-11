package br.com.jhonecmd.courses_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final String[] PERMIT_ALL_LIST = {
            "/",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger/resources/**",
            "/actuator/**"
    };

    private final SecurityFilter securityFilter;

    SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(
                        (auth) -> auth.requestMatchers("/users").permitAll()
                                .requestMatchers("/users/auth").permitAll()
                                .requestMatchers("/users/change-password").permitAll()
                                .requestMatchers("/courses/v2").permitAll()
                                .requestMatchers(PERMIT_ALL_LIST).permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(securityFilter, BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
