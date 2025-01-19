package com.project.courseapp.components;

import com.project.courseapp.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    @Value("${jwt.expiration}")
    private int expirationTime;
    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("phoneNumber", user.getPhoneNumber());
        try{
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000L))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        }catch (Exception e){
            throw new InvalidParameterException(("Can't generate token, reason: " + e.getMessage()));
        }
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaims(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    public String extractPhoneNumber(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String phoneNumber = extractPhoneNumber(token);
        return phoneNumber.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
