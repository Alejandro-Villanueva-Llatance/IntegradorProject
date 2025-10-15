package com.parkinsoncare.servicio;

import com.parkinsoncare.configuracion.UtilidadesJWT;
import com.parkinsoncare.modelo.Paciente;
import com.parkinsoncare.modelo.Usuario;
import com.parkinsoncare.repositorio.RepositorioPaciente;
import com.parkinsoncare.repositorio.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuarioAutenticado {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioPaciente repositorioPaciente;

    @Autowired
    private UtilidadesJWT utilidadesJWT;

    @Autowired
    private ServicioDetallesUsuario servicioDetallesUsuario;

    public Usuario obtenerUsuarioAutenticado() {
        String username = utilidadesJWT.obtenerUsernameDelContexto();
        return servicioDetallesUsuario.obtenerUsuarioPorUsername(username);
    }

    public Long obtenerUsuarioIdAutenticado() {
        return utilidadesJWT.obtenerUsuarioIdDelContexto();
    }

    public Paciente obtenerPacienteAutenticado() {
        Usuario usuario = obtenerUsuarioAutenticado();


        if (!usuario.getRol().name().equals("PACIENTE")) {
            throw new RuntimeException("El usuario autenticado no es un paciente. Rol: " + usuario.getRol().name());
        }

        return repositorioPaciente.findById(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado para el usuario: " + usuario.getUsername()));
    }

    public Long obtenerPacienteIdAutenticado() {
        System.out.println("🔍 Obteniendo paciente ID para usuario autenticado...");

        Usuario usuario = obtenerUsuarioAutenticado();
        System.out.println("👤 Usuario autenticado: " + usuario.getUsername() + ", Rol: " + usuario.getRol());

        if (!usuario.getRol().name().equals("PACIENTE")) {
            System.out.println("❌ El usuario no es un paciente, es: " + usuario.getRol().name());
            throw new RuntimeException("El usuario autenticado no es un paciente");
        }

        try {
            Paciente paciente = repositorioPaciente.findById(usuario.getId())
                    .orElseThrow(() -> {
                        System.out.println("❌ No se encontró paciente para usuario ID: " + usuario.getId());
                        return new RuntimeException("Paciente no encontrado para el usuario: " + usuario.getUsername());
                    });

            System.out.println("✅ Paciente encontrado: " + paciente.getId());
            return paciente.getId();
        } catch (Exception e) {
            System.out.println("💥 Error obteniendo paciente: " + e.getMessage());
            throw e;
        }
    }

    public String obtenerRolUsuarioAutenticado() {
        return utilidadesJWT.obtenerRolDelContexto();
    }

    public boolean esPaciente() {
        return "PACIENTE".equals(obtenerRolUsuarioAutenticado());
    }

    public boolean esDoctor() {
        return "DOCTOR".equals(obtenerRolUsuarioAutenticado());
    }

    public boolean esAdmin() {
        return "ADMIN".equals(obtenerRolUsuarioAutenticado());
    }

    public String obtenerNombreUsuarioAutenticado() {
        return obtenerUsuarioAutenticado().getNombre();
    }

    public boolean tieneAccesoAPaciente(Long pacienteIdSolicitado) {
        if (esAdmin() || esDoctor()) {
            return true; 
        }

        if (esPaciente()) {
            Long pacienteIdAutenticado = obtenerPacienteIdAutenticado();
            return pacienteIdAutenticado.equals(pacienteIdSolicitado);
        }

        return false;
    }
}