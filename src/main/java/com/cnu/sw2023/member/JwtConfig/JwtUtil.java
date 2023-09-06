package com.cnu.sw2023.member.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@RequiredArgsConstructor
public class JwtUtil {


    public static String getUserName(String token){
        return Jwts.parser().setSigningKey("asdf1zxcvp,qwefqwe21;3lm12;rasdf").parseClaimsJws(token)
                .getBody().get("email", String.class);
    }

    public static String getUserName(String token , String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("email", String.class);
    }

    public static boolean isExpired(String token , String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }
    public static String createJwt(String email,String secretKey , Long expiredMS){
        Claims claims = Jwts.claims();
        claims.put("email",email);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMS))
                .signWith(SignatureAlgorithm.HS256 , secretKey)
                .compact();
    }
}
