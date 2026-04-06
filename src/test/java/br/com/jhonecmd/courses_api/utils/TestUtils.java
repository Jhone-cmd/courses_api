package br.com.jhonecmd.courses_api.utils;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
    public static String objectToJson(Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String generateToken(UUID userId, String role, RSAPublicKey publicKey, RSAPrivateKey privateKey) {

        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        return JWT.create()
                .withIssuer("courses-api")
                .withSubject(userId.toString())
                .withClaim("roles", Arrays.asList(role))
                .withExpiresAt(expiresIn)
                .sign(algorithm);
    }
}
