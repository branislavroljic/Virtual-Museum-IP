package org.unibl.etf.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.unibl.etf.models.entities.UserEntity;

import java.util.Date;

public class JwtUtil {

    public static final long JWT_EXPIRATION_TIME = 15*60 * 1000; // 15 min
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 60*60 * 1000; // 1 hour
    public static final String SECRET = "fsaf078fasfas098asfafas8089df866"; //ovo je turjino


    public static String generateJwt(UserEntity user) {
        return Jwts.builder()
                .setId(user.getId().toString())
                .setSubject(user.getUsername())
                .claim("role", user.getRole().name())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static String generateRefresh(UserEntity user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static Claims parseJwt(String token){
       return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token).getBody();
    }
}
