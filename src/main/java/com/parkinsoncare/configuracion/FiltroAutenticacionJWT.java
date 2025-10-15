package com.parkinsoncare.configuracion;

import com.parkinsoncare.servicio.ServicioDetallesUsuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FiltroAutenticacionJWT extends OncePerRequestFilter {

    @Autowired
    private UtilidadesJWT utilidadesJWT;

    @Autowired
    private ServicioDetallesUsuario servicioDetallesUsuario;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {



        try {
            String jwt = obtenerJWTDeRequest(request);
            System.out.println("📨 Token recibido: " + (jwt != null ? jwt.substring(0, 20) + "..." : "NULL"));

            if (jwt != null && utilidadesJWT.validarToken(jwt)) {
                String username = utilidadesJWT.obtenerUsernameDeToken(jwt);
                System.out.println("Token VÁLIDO para usuario: " + username);

                UserDetails userDetails = servicioDetallesUsuario.loadUserByUsername(username);
                System.out.println(" Authorities: " + userDetails.getAuthorities());

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Autenticación establecida en SecurityContext");
            } else {
                System.out.println("❌ Token INVÁLIDO o ausente");
            }
        } catch (Exception e) {
            System.out.println("💥 ERROR en filtro: " + e.getMessage());
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }


    private String obtenerJWTDeRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}