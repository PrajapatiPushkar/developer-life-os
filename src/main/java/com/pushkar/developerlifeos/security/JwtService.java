package com.pushkar.developerlifeos.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(String username){

        SecretKey key = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()

                .subject(username)

                .issuedAt(new Date())

                .expiration(new Date(
                        System.currentTimeMillis()
                                + expiration))

                .signWith(key)

                .compact();

    }

    public String extractUsername(String token){

        SecretKey key = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.parser()

                .verifyWith(key)

                .build()

                .parseSignedClaims(token)

                .getPayload()

                .getSubject();

    }

    public boolean isTokenValid(String token){

        try{

            extractUsername(token);

            return true;

        }catch(Exception e){

            return false;

        }

    }

}