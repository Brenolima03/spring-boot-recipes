package com.br.recipes.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.br.recipes.entities.User;

@Service
public class TokenService {
  @Value("${api.security.token.secret}")
  private String secret;

  public String generateToken(User user){
    try{
      Algorithm algorithm = Algorithm.HMAC256(secret);
      String token = JWT.create()
        .withIssuer("auth-api")
        .withSubject(user.getLogin())
        .withClaim("role", user.getRole().name())
        .withExpiresAt(genExpirationDate())
        .sign(algorithm);

      return token;
    } catch (JWTCreationException exception) {
      throw new RuntimeException("Error while generating token", exception);
    }
  }

  public String[] validateTokenWithRole(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      DecodedJWT decodedJWT = JWT.require(algorithm)
        .withIssuer("auth-api")
        .build()
        .verify(token);

      String login = decodedJWT.getSubject();
      String role = decodedJWT.getClaim("role").asString();

      return new String[] {login, role};
    } catch (JWTVerificationException exception) {
      return new String[] {"", ""};
    }
  }

  private Instant genExpirationDate(){
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}
