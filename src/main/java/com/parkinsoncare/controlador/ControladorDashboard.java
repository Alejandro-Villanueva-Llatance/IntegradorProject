package com.parkinsoncare.controlador;

import com.parkinsoncare.dto.RespuestaAPI;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class ControladorDashboard {

    @GetMapping("/estadisticas")
    public RespuestaAPI obtenerEstadisticas() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalPacientes", 15);
        stats.put("citasHoy", 3);
        stats.put("recomendacionesPendientes", 7);
        stats.put("sintomasRegistrados", 24);

        return RespuestaAPI.success("Estadísticas del dashboard", stats);
    }

    @GetMapping("/recomendaciones")
    public RespuestaAPI obtenerRecomendaciones() {
        Map<String, Object> recomendaciones = new HashMap<>();
        recomendaciones.put("ejercicios", "Realizar ejercicios de estiramiento suaves");
        recomendaciones.put("alimentacion", "Mantener hidratación adecuada");
        recomendaciones.put("descanso", "Descansar 8 horas diarias");

        return RespuestaAPI.success("Recomendaciones personalizadas", recomendaciones);
    }
}