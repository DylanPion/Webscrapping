package com.WebCrawling.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private SecretKey secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
    private long jwtExpiration;
    private long refreshExpiration;

    // Méthode qui extrait tout les claims (informations) du Token
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey) // Configure la clé de signature que le parser utilisera pour vérifier la
                                          // validité du token. La méthode getSignInKey retournera la clé de
                                          // signature appropriée.
                .build() // Finalise le parser et le construit
                .parseClaimsJws(token) // Cette méthode est appelé sur le parser pour analyser le token
                .getBody(); // Permet d'obtenir le corps (payload) du token qui contient les claims (infos)
    }

    // Méthode qui permet d'extraire un claim spécifique du Token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Méthode qui extrait le Username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Méthod qui génère un Token
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Méthode pour générer un Token
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    // Méthode pour raffraichir le Token
    public String generateRefreshToken(
            UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    // Méthode pour construire le Token
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration) {
        return Jwts
                .builder() // créer un constructeur de token JWT
                .setClaims(extraClaims) // permet de définir les claims à donner au token
                .setSubject(userDetails.getUsername()) // définit le champ subject en utilisant le username
                .setIssuedAt(new Date(System.currentTimeMillis())) // date émission (création) du token
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Date expiration token
                .signWith(secretKey, SignatureAlgorithm.HS256) // Signe le token avec la secretKey
                .compact(); // Appel la méthode compact pour obtenir la représentation sous forme de
                            // caractère du tokenc créer
    }

    // Valide le TOken si username est égal au username extrait du token et que le
    // token n'est pas expiré
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Méthode qui vérifie si le Token est expiré
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Méthode qui extrait la date d'expiration du Token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
