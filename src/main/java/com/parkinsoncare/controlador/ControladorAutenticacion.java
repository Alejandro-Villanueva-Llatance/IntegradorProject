package com.parkinsoncare.controlador;

import com.parkinsoncare.dto.autenticacion.SolicitudLogin;
import com.parkinsoncare.dto.autenticacion.SolicitudRegistro;
import com.parkinsoncare.dto.autenticacion.RespuestaLogin;
import com.parkinsoncare.dto.RespuestaAPI;
import com.parkinsoncare.modelo.Usuario;
import com.parkinsoncare.modelo.enumeraciones.RolUsuario;
import com.parkinsoncare.servicio.ServicioAutenticacion;
import com.parkinsoncare.servicio.ServicioDetallesUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class ControladorAutenticacion {

    @Autowired
    private ServicioAutenticacion servicioAutenticacion;
    @Autowired
    private ServicioDetallesUsuario servicioDetallesUsuario;

    @PostMapping("/login")
    public RespuestaAPI login(@RequestBody SolicitudLogin solicitud) {
        try {
            String token = servicioAutenticacion.autenticarUsuario(solicitud.getUsername(), solicitud.getPassword());


            Usuario usuario = servicioDetallesUsuario.obtenerUsuarioPorUsername(solicitud.getUsername());


            RespuestaLogin respuesta = new RespuestaLogin(
                    token,
                    usuario.getUsername(),
                    usuario.getRol().name(),
                    usuario.getNombre()
            );

            return RespuestaAPI.success("Login exitoso", respuesta);
        } catch (Exception e) {
            return RespuestaAPI.error("Credenciales inválidas: " + e.getMessage());
        }
    }

    @PostMapping("/registro/paciente")
    public RespuestaAPI registroPaciente(@RequestBody SolicitudRegistro solicitud) {
        try {
            Usuario usuario = servicioAutenticacion.registrarUsuario(
                    solicitud.getUsername(),
                    solicitud.getPassword(),
                    solicitud.getEmail(),
                    solicitud.getNombre(),
                    RolUsuario.PACIENTE
            );
            return RespuestaAPI.success("Paciente registrado exitosamente", usuario);
        } catch (Exception e) {
            return RespuestaAPI.error("Error en registro: " + e.getMessage());
        }
    }

    @PostMapping("/registro/doctor")
    public RespuestaAPI registroDoctor(@RequestBody SolicitudRegistro solicitud) {
        try {
            Usuario usuario = servicioAutenticacion.registrarUsuario(
                    solicitud.getUsername(),
                    solicitud.getPassword(),
                    solicitud.getEmail(),
                    solicitud.getNombre(),
                    RolUsuario.DOCTOR
            );
            return RespuestaAPI.success("Doctor registrado exitosamente", usuario);
        } catch (Exception e) {
            return RespuestaAPI.error("Error en registro: " + e.getMessage());
        }
    }

    @PostMapping("/registro/admin")
    public RespuestaAPI registroAdmin(@RequestBody SolicitudRegistro solicitud) {
        try {
            Usuario usuario = servicioAutenticacion.registrarUsuario(
                    solicitud.getUsername(),
                    solicitud.getPassword(),
                    solicitud.getEmail(),
                    solicitud.getNombre(),
                    RolUsuario.ADMIN
            );
            return RespuestaAPI.success("Admin registrado exitosamente", usuario);
        } catch (Exception e) {
            return RespuestaAPI.error("Error en registro: " + e.getMessage());
        }
    }
}