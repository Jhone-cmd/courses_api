package br.com.jhonecmd.courses_api.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.jhonecmd.courses_api.providers.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        System.out.println(header);
        if (header != null) {
            var token = this.jwtProvider.validateToken(header);

            if (token == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            request.setAttribute("userId", token.getSubject());

            var roles = token.getClaim("roles").asList(Object.class);

            var grants = roles.stream()
                    .map((role) -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())).toList();

            System.out.println(grants);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null,
                    grants);

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
