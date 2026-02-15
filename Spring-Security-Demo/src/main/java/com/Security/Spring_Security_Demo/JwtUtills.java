package com.Security.Spring_Security_Demo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtills {

    private String jwtToken = "YS1zdHJpbmctc2VjcmV0LWF0LWxlYXN0LTI1Ni1iaXRzLWxvbmcK";
    private int ExpairyTimeMs = 172800000;

    public String genrateJwtTokenFromUserName(String userName) {
        return Jwts.builder()
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + ExpairyTimeMs))
                .signWith(key())
                .compact();
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        String BearerToken = request.getHeader("Authorization");
        if (BearerToken != null && BearerToken.startsWith("Bearer "))
            return BearerToken.substring(7);
        return null;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtToken));
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public String getUsernameFromToken(String jwt) {
          return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(jwt).getPayload().getSubject();

    }
}
