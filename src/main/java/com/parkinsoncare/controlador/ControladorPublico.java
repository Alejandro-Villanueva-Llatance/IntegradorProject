package com.parkinsoncare.controlador;

import com.parkinsoncare.dto.RespuestaAPI;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*")
public class ControladorPublico {

    @GetMapping("/test")
    public RespuestaAPI test() {
        return RespuestaAPI.success("✅ Backend Parkinson Care funcionando correctamente!",
                "Sistema de autenticación JWT activo");
    }

    @GetMapping("/info")
    public RespuestaAPI info() {
        return RespuestaAPI.success("🏥 Sistema de Cuidado para Parkinson",
                "Backend Spring Boot con autenticación JWT");
    }

    @GetMapping("/health")
    public RespuestaAPI health() {
        return RespuestaAPI.success("🔧 Estado del sistema",
                "Servidor funcionando correctamente - Puerto 8081");
    }
}