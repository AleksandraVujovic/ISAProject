package com.example.BloodBank.Utils;

import com.example.BloodBank.model.HeadAdmin;
import com.example.BloodBank.service.CustomUserDetailsService;
import com.example.BloodBank.service.HeadAdminService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private String secret = "javatechie";
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private HeadAdminService headAdminService;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        UserDetails test = userDetailsService.loadUserByUsername(username);
        userDetailsService.loadUserByUsername(username).getAuthorities();
        claims.put("role", userDetailsService.loadUserByUsername(username).getAuthorities().toArray()[1]);
        claims.put("id", userDetailsService.loadUserByUsername(username).getAuthorities().toArray()[0]);

        if(userDetailsService.loadUserByUsername(username).getAuthorities().toArray()[1].toString().equals("ROLE_HEADADMIN")){
            if(!headAdminService.findByUsername(username).get().isPasswordChanged())
                claims.put("isPasswordChanged", "false");
            else
                claims.put("isPasswordChanged", "true");
        }
        else
            claims.put("isPasswordChanged", "notHeadAdmin");
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

