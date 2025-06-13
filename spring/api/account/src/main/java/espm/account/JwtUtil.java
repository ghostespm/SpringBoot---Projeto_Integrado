package espm.account;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtil {

    // Em produção, use uma variável de ambiente ou arquivo seguro
    private static final String SECRET_KEY = "sua-chave-secreta-aqui-sua-chave-secreta-aqui"; // mínimo 32 chars para HS256

    // Gera um token JWT válido por 1 hora
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    // Extrai o e-mail do token
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // Valida se o token está correto e não expirou
    public boolean isTokenValid(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // Recupera os claims do token
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }
}