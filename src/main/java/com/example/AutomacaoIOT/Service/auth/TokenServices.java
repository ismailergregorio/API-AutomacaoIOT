package com.example.AutomacaoIOT.Service.auth;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.AutomacaoIOT.Model.ModelUser.ModelUser;

@Service
public class TokenServices {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${time.token}")
    private Integer timeToken;

    @Value("${time.refreshtoken}")
    private Integer timeRefreshToken;

    public String geraToken(
            ModelUser usuario) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(usuario.getEmail())
                    .withClaim("type", "ACCESS")
                    .withClaim("id", usuario.getId())
                    .withClaim("nome", usuario.getNome())
                    .withClaim("sobrenome", usuario.getSobrenome())
                    .withClaim("status", usuario.getStatus())
                    .withClaim("telefone", usuario.getTelefone())
                    .withClaim("role", usuario.getRole().getRole())
                    .withExpiresAt(geraDataExpiracao(timeToken))
                    .sign(algorithm);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar token", e);
        }
    }

    public String gerarRefreshToken(
            ModelUser usuario) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(usuario.getEmail())
                    .withClaim("type", "REFRESH")
                    .withClaim("id", usuario.getId())
                    .withClaim("nome", usuario.getNome())
                    .withClaim("sobrenome", usuario.getSobrenome())
                    .withClaim("status", usuario.getStatus())
                    .withClaim("telefone", usuario.getTelefone())
                    .withClaim("role", usuario.getRole().getRole())
                    .withExpiresAt(geraDataExpiracao(timeRefreshToken))
                    .sign(algorithm);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar token", e);
        }
    }

    public String validarToken(String token) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT jwt = JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token);

            if (!"ACCESS".equals(jwt.getClaim("type").asString())) {
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Token inválido.");
            }

            return jwt.getSubject();

        } catch (JWTVerificationException e) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Token inválido ou expirado");
        }
    }

    public String validarRefreshToken(String token) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT jwt = JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token);

            if (!"REFRESH".equals(jwt.getClaim("type").asString())) {
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Token inválido.");
            }

            return jwt.getSubject();

        } catch (JWTVerificationException e) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Token inválido ou expirado");
        }
    }

    private Instant geraDataExpiracao(Integer tempoExpiracao) {

        return LocalDateTime.now()
                .plusMinutes(tempoExpiracao)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}