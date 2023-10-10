package uz.feliza.felizabackend.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.entity.User;

import java.security.Key;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${app.jwt.secret}")
    private String secretKey;

    public String generateAccessToken(Customer customer) throws ParseException {
        return generateAccessToken(new HashMap<>(),customer);
    }

    public String generateAccessToken(Map<String,Object> extraClaims, Customer customer){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(String.format("%s,%s", customer.getId(), customer.getEmail()))
                .setIssuer("CodeJava")
                .claim("roles", customer.getRoles().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateAccessToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException e){
            LOGGER.error("JWT expired",e);
        }catch (IllegalArgumentException e){
            LOGGER.error("Token is null, empty or has only whitespace",  e);
        }catch (MalformedJwtException e){
            LOGGER.error("Token is invalid", e);
        }catch (UnsupportedJwtException e){
            LOGGER.error("JWt is not supported",e);
        }catch (JwtException e){
            LOGGER.error("JWT exception", e);
        }
        return false;
    }

    public Claims parseClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
