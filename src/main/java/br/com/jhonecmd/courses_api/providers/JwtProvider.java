package br.com.jhonecmd.courses_api.providers;

import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JwtProvider {

    @Autowired
    private RSAPublicKey publicKey;

    @Value("${security.token}")
    private String secretKey;

    public DecodedJWT validateToken(String token) {

        token = token.replace("Bearer ", "");
        Algorithm algorithm = Algorithm.RSA256(publicKey);

        try {

            var tokenDecoded = JWT.require(algorithm).build().verify(token);
            return tokenDecoded;

        } catch (JWTVerificationException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
