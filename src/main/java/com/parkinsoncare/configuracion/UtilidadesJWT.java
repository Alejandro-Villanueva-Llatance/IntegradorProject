package com.parkinsoncare.configuracion;

import com.parkinsoncare.servicio.ServicioDetallesUsuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class UtilidadesJWT {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    @Autowired
    private ServicioDetallesUsuario servicioDetallesUsuario;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generarToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + jwtExpirationMs);

        // Obtener el usuario completo para extraer información adicional
        com.parkinsoncare.modelo.Usuario usuario =
                servicioDetallesUsuario.obtenerUsuarioPorUsername(userDetails.getUsername());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("id", usuario.getId())
                .claim("nombre", usuario.getNombre())
                .claim("rol", usuario.getRol().name())
                .setIssuedAt(ahora)
                .setExpiration(expiracion)
                .signWith(getSigningKey())
                .compact();
    }

    public String obtenerUsernameDeToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validarToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            System.out.println("✅ Token válido - Usuario: " + claims.getSubject() + ", Rol: " + claims.get("rol"));
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("❌ Token inválido: " + e.getMessage());
            return false;
        }
    }



    public Long obtenerUsuarioIdDelToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("id", Long.class);
    }

    public String obtenerRolDelToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("rol", String.class);
    }

    public String obtenerNombreDelToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("nombre", String.class);
    }

    public String obtenerTokenDelHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    public String obtenerUsernameDelContexto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        throw new RuntimeException("Usuario no autenticado");
    }

    public Long obtenerUsuarioIdDelContexto() {
        String username = obtenerUsernameDelContexto();
        com.parkinsoncare.modelo.Usuario usuario = servicioDetallesUsuario.obtenerUsuarioPorUsername(username);
        return usuario.getId();
    }

    public String obtenerRolDelContexto() {
        String username = obtenerUsernameDelContexto();
        com.parkinsoncare.modelo.Usuario usuario = servicioDetallesUsuario.obtenerUsuarioPorUsername(username);
        return usuario.getRol().name();
    }
}