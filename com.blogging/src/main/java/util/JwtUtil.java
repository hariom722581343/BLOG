package util;

import java.util.Date;

import org.springframework.stereotype.Component;

import entity.User;

@Component
public class JwtUtil {

    private static final String SECRET = "your-secret-key"; // Replace with a secure secret

    public static String generateToken(User user) {
        return Jwts.builder()
            .setSubject(user.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000))  // 24 hours
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();
    }

    public User getUserFromToken(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token)
            .getBody();
        return new User(claims.getSubject(), "");  // Assuming user has username in token and password is not needed
    }
}
