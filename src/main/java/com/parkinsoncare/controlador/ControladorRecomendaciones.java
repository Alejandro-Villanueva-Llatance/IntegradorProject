package com.parkinsoncare.controlador;

import com.parkinsoncare.dto.RespuestaAPI;
import com.parkinsoncare.modelo.Recomendacion;
import com.parkinsoncare.servicio.ServicioRecomendacion;
import com.parkinsoncare.servicio.ServicioUsuarioAutenticado;
import com.parkinsoncare.servicio.ServicioIA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recomendaciones")
@CrossOrigin(origins = "*")
public class ControladorRecomendaciones {

    @Autowired
    private ServicioRecomendacion servicioRecomendacion;

    @Autowired
    private ServicioUsuarioAutenticado servicioUsuarioAutenticado;

    @Autowired
    private ServicioIA servicioIA;




    @GetMapping
    public RespuestaAPI obtenerRecomendaciones() {
        try {
            // Verificar que el usuario es un paciente
            if (!servicioUsuarioAutenticado.esPaciente()) {
                return RespuestaAPI.error("Solo los pacientes pueden ver recomendaciones");
            }

            Long pacienteId = servicioUsuarioAutenticado.obtenerPacienteIdAutenticado();
            List<Recomendacion> recomendaciones = servicioRecomendacion.obtenerRecomendacionesPaciente(pacienteId);

            return RespuestaAPI.success("Recomendaciones personalizadas", recomendaciones);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener recomendaciones: " + e.getMessage());
        }
    }

    @PostMapping("/generar")
    public RespuestaAPI generarRecomendaciones() {
        try {
            // Verificar que el usuario es un paciente
            if (!servicioUsuarioAutenticado.esPaciente()) {
                return RespuestaAPI.error("Solo los pacientes pueden generar recomendaciones");
            }

            Long pacienteId = servicioUsuarioAutenticado.obtenerPacienteIdAutenticado();
            List<Recomendacion> recomendaciones = servicioRecomendacion.generarRecomendacionesPersonalizadas(pacienteId);

            return RespuestaAPI.success("Recomendaciones generadas exitosamente", recomendaciones);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al generar recomendaciones: " + e.getMessage());
        }
    }

    @GetMapping("/ejercicios")
    public RespuestaAPI obtenerEjercicios() {
        try {
            if (!servicioUsuarioAutenticado.esPaciente()) {
                return RespuestaAPI.error("Solo los pacientes pueden ver ejercicios");
            }

            Long pacienteId = servicioUsuarioAutenticado.obtenerPacienteIdAutenticado();
            List<Recomendacion> ejercicios = servicioRecomendacion.obtenerRecomendacionesPaciente(pacienteId)
                    .stream()
                    .filter(rec -> "EJERCICIO".equals(rec.getCategoria()))
                    .toList();

            return RespuestaAPI.success("Ejercicios recomendados", ejercicios);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener ejercicios: " + e.getMessage());
        }
    }

    @GetMapping("/consejos")
    public RespuestaAPI obtenerConsejos() {
        try {
            if (!servicioUsuarioAutenticado.esPaciente()) {
                return RespuestaAPI.error("Solo los pacientes pueden ver consejos");
            }

            Long pacienteId = servicioUsuarioAutenticado.obtenerPacienteIdAutenticado();
            List<Recomendacion> consejos = servicioRecomendacion.obtenerRecomendacionesPaciente(pacienteId)
                    .stream()
                    .filter(rec -> "NUTRICION".equals(rec.getCategoria()) || "DESCANSO".equals(rec.getCategoria()))
                    .toList();

            return RespuestaAPI.success("Consejos diarios", consejos);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener consejos: " + e.getMessage());
        }
    }

    @PutMapping("/{recomendacionId}/completar")
    public RespuestaAPI marcarComoCompletada(@PathVariable Long recomendacionId) {
        try {
            // En una implementación completa, agregarías lógica para marcar como completada
            return RespuestaAPI.success("Recomendación marcada como completada", null);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al actualizar recomendación: " + e.getMessage());
        }
    }

    // Endpoint para doctores que pueden ver recomendaciones de sus pacientes
    @GetMapping("/paciente/{pacienteId}")
    public RespuestaAPI obtenerRecomendacionesPaciente(@PathVariable Long pacienteId) {
        try {
            // Verificar que el usuario autenticado tiene acceso a este paciente
            if (!servicioUsuarioAutenticado.tieneAccesoAPaciente(pacienteId)) {
                return RespuestaAPI.error("No autorizado para ver recomendaciones de este paciente");
            }

            List<Recomendacion> recomendaciones = servicioRecomendacion.obtenerRecomendacionesPaciente(pacienteId);
            return RespuestaAPI.success("Recomendaciones del paciente", recomendaciones);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener recomendaciones: " + e.getMessage());
        }
    }
    @GetMapping("/estado-ia")
    public RespuestaAPI verificarEstadoIA() {
        try {
            boolean iaDisponible = servicioIA.verificarConexionIA();
            Map<String, Object> estado = new HashMap<>();
            estado.put("iaDisponible", iaDisponible);
            estado.put("mensaje", iaDisponible ?
                    "✅ IA configurada y funcionando" :
                    "⚠️ IA no configurada, usando reglas de negocio");

            return RespuestaAPI.success("Estado del sistema de IA", estado);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al verificar estado de IA: " + e.getMessage());
        }
    }
}