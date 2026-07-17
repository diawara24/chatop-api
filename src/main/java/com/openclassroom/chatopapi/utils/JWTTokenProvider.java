package com.openclassroom.chatopapi.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;



@Component
public class JWTTokenProvider {


    @Value("${jwt.secret}")
    private String secret;



    @Value("${jwt.expiration}")
    private long expiration;



    public String generateToken(UserDetails userDetails){

        Map<String,Object> claims =
                new HashMap<>();

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey())
                .compact();
    }



    private SecretKey getSignKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }


    public Date extractExpiration( String token){

        return extractClaim(token, Claims::getExpiration);

    }




    public <T> T extractClaim(String token, Function<Claims,T> resolver){

        Claims claims = Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return resolver.apply(claims);

    }

    public boolean validateToken(String token, UserDetails userDetails){

        String email = extractUsername(token);

        return email.equals(userDetails.getUsername()) && extractExpiration(token).after(new Date());

    }

}