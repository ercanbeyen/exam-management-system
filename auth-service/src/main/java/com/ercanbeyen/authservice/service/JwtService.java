package com.ercanbeyen.authservice.service;

import com.ercanbeyen.authservice.constant.message.JwtMessage;
import com.ercanbeyen.authservice.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtService {
    private static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    private final UserDetailsService userDetailsService;

    public Map<String, String> generateTokens(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return generateTokens(claims, userDetails);
    }
    public Map<String, String> refreshToken(String token) {
        String username = extractUsername(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String accessToken = generateAccessToken(new HashMap<>(), userDetails);

        Map<String, String> tokens = new HashMap<>();
        tokens.put(JwtMessage.ACCESS_TOKEN, accessToken);
        tokens.put(JwtMessage.REFRESH_TOKEN_TOKEN, token);

        return tokens;
    }

    public void validateToken(final String token) {
        extractJwsClaims(token);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean hasRole(String token, String role) {
        final Claims claims = extractAllClaims(token);
        List<String> authorities = (List<String>) claims.get("roles");
        return authorities.stream()
                .anyMatch(authority -> authority.equals(role));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return extractJwsClaims(token)
                .getBody();
    }

    private Map<String, String> generateTokens(Map<String, Object> claims, UserDetails userDetails) {
        String username = userDetails.getUsername();
        Map<String, String> tokens = new HashMap<>();

        String accessToken = generateAccessToken(claims, userDetails);
        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setExpiration(JwtUtil.calculateExpirationDate(60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

        tokens.put(JwtMessage.ACCESS_TOKEN, accessToken);
        tokens.put(JwtMessage.REFRESH_TOKEN_TOKEN, refreshToken);

        return tokens;
    }

    private String generateAccessToken(Map<String, Object> claims, UserDetails userDetails) {
        List<String> authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        claims.put("roles", authorities);
        String username = userDetails.getUsername();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(JwtUtil.calculateExpirationDate(15))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Jws<Claims> extractJwsClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
