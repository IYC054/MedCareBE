package fpt.aptech.pjs4.configs;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Tạo khóa bí mật

    public String createToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Hết hạn sau 1 ngày
                .signWith(key)
                .compact();
    }
}
