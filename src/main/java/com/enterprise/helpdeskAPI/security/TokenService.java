package com.enterprise.helpdeskAPI.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.token.key}")
    private String key;

    @Value("${jwt.token.expiration_time}")
    private int expirationTime;

    public String generateToken(UserSecurity user){

        return Jwts.builder()
                .setExpiration(getExpirationToken())
                .setSubject(user.getUsername())
                .claim("ID", user.getId())
                .signWith(getKeyToken())
                .compact();
    }

    public String getSubject(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKeyToken())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Date getExpirationToken(){
        Calendar expirationDate = Calendar.getInstance();
        expirationDate.add(Calendar.MONTH, expirationTime);
        return expirationDate.getTime();
    }

    public Key getKeyToken(){
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

}
