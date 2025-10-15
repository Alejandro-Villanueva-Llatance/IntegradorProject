package com.parkinsoncare.servicio;

import com.parkinsoncare.configuracion.UtilidadesJWT;
import com.parkinsoncare.modelo.Usuario;
import com.parkinsoncare.modelo.enumeraciones.RolUsuario;
import com.parkinsoncare.repositorio.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicioAutenticacion {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UtilidadesJWT utilidadesJWT;

    public Long obtenerUsuarioAutenticadoId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<Usuario> usuarioOpt = repositorioUsuario.findByUsername(username);
            if (usuarioOpt.isPresent()) {
                return usuarioOpt.get().getId();
            }
        }
        throw new RuntimeException("Usuario no autenticado");
    }

    public Usuario registrarUsuario(String username, String password, String email, String nombre, RolUsuario rol) {
        if (repositorioUsuario.existsByUsername(username)) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        if (repositorioUsuario.existsByEmail(email)) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = new Usuario(username, passwordEncoder.encode(password), email, nombre, rol);
        return repositorioUsuario.save(usuario);
    }

    public String autenticarUsuario(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return utilidadesJWT.generarToken(authentication);
    }
}