package com.parkinsoncare.controlador;

import com.parkinsoncare.dto.RespuestaAPI;
import com.parkinsoncare.modelo.enumeraciones.TipoContenido;
import com.parkinsoncare.servicio.ServicioContenidoEducativo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contenido-educativo")
@CrossOrigin(origins = "*")
public class ControladorContenidoEducativo {

    @Autowired
    private ServicioContenidoEducativo servicioContenidoEducativo;

    @GetMapping
    public RespuestaAPI obtenerTodoContenido() {
        try {
            return RespuestaAPI.success("Contenido educativo disponible",
                    servicioContenidoEducativo.obtenerTodoContenidoActivo());
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener contenido: " + e.getMessage());
        }
    }

    @GetMapping("/ejercicios")
    public RespuestaAPI obtenerEjercicios() {
        try {
            return RespuestaAPI.success("Ejercicios recomendados",
                    servicioContenidoEducativo.obtenerEjercicios());
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener ejercicios: " + e.getMessage());
        }
    }

    @GetMapping("/nutricion")
    public RespuestaAPI obtenerConsejosNutricion() {
        try {
            return RespuestaAPI.success("Consejos de nutrición",
                    servicioContenidoEducativo.obtenerConsejosNutricion());
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener consejos de nutrición: " + e.getMessage());
        }
    }

    @GetMapping("/consejos-diarios")
    public RespuestaAPI obtenerConsejosDiarios() {
        try {
            return RespuestaAPI.success("Consejos diarios",
                    servicioContenidoEducativo.obtenerConsejosDiarios());
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener consejos diarios: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public RespuestaAPI obtenerContenidoPorId(@PathVariable Long id) {
        try {
            return RespuestaAPI.success("Contenido educativo",
                    servicioContenidoEducativo.obtenerContenidoPorId(id));
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener contenido: " + e.getMessage());
        }
    }
}