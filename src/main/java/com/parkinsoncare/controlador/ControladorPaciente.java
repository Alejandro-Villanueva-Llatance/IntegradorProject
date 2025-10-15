package com.parkinsoncare.controlador;

import com.parkinsoncare.dto.RespuestaAPI;
import com.parkinsoncare.modelo.Paciente;
import com.parkinsoncare.repositorio.RepositorioPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paciente")
@CrossOrigin(origins = "*")
public class ControladorPaciente {

    @Autowired
    private RepositorioPaciente repositorioPaciente;

    @GetMapping("/perfil")
    public RespuestaAPI obtenerPerfil() {
        // En una implementación real, obtendrías el ID del paciente del token JWT
        return RespuestaAPI.success("Perfil del paciente",
                "Aquí iría la información completa del paciente");
    }

    @GetMapping("/estadisticas")
    public RespuestaAPI obtenerEstadisticas() {
        return RespuestaAPI.success("Estadísticas del paciente",
                "Progreso, síntomas, medicamentos, etc.");
    }

    @GetMapping("/sintomas")
    public RespuestaAPI obtenerHistorialSintomas() {
        return RespuestaAPI.success("Historial de síntomas",
                "Registros de síntomas del paciente");
    }

    @GetMapping("/citas")
    public RespuestaAPI obtenerCitas() {
        return RespuestaAPI.success("Mis citas",
                "Lista de citas programadas del paciente");
    }

    @GetMapping("/recomendaciones")
    public RespuestaAPI obtenerRecomendaciones() {
        return RespuestaAPI.success("Recomendaciones personalizadas",
                "Ejercicios, consejos y recomendaciones basadas en el perfil");
    }
}