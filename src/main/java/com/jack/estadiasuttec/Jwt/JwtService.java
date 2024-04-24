package com.jack.estadiasuttec.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    // Método privado para generar un token JWT con reclamaciones adicionales
    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)                // Define reclamaciones adicionales
                .setSubject(user.getUsername())         // Define el sujeto del token como el nombre de usuario
                .setIssuedAt(new Date(System.currentTimeMillis()))  // Define la fecha de emisión del token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))  // Define la fecha de expiración del token (24 horas)
                .signWith(getKey(), SignatureAlgorithm.HS256)  // Firma el token con el algoritmo HS256 y la clave secreta
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);  // Decodifica la clave secreta de Base64
        return Keys.hmacShaKeyFor(keyBytes);  // Crea una clave HMAC a partir de los bytes decodificados

    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())  // Define la clave secreta para verificar la firma del token
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String nombre = getUsernameFromToken(token);
        return (nombre.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }
}
