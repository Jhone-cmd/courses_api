package br.com.jhonecmd.courses_api.modules.users.usecases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.jhonecmd.courses_api.exceptions.InvalidCredentials;
import br.com.jhonecmd.courses_api.modules.users.dto.AuthUserDTO;
import br.com.jhonecmd.courses_api.modules.users.dto.AuthUserResponseDTO;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;

@Service
public class AuthenticateUserUseCase {

    @Value("${security.token}")
    private String secretKey;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthUserResponseDTO execute(AuthUserDTO authUserDTO) {
        var user = this.userRepository.findByEmail(authUserDTO.getEmail()).orElseThrow(() -> {
            throw new InvalidCredentials();
        });

        var passwordMatches = this.passwordEncoder.matches(authUserDTO.getPassword(), user.getPassword());

        if (!passwordMatches) {
            throw new InvalidCredentials();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofDays(7));

        var token = JWT.create().withIssuer("courses-api").withSubject(user.getId().toString()).withExpiresAt(expiresIn)
                .withClaim("roles",
                        Arrays.asList(user.getPosition().toString()))
                .sign(algorithm);

        var authUserResponseDTO = AuthUserResponseDTO.builder().access_token(token).expiresAt(expiresIn.toEpochMilli())
                .position(user.getPosition().toString())
                .build();

        return authUserResponseDTO;
    }
}
