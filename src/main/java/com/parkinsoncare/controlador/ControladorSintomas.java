package com.parkinsoncare.controlador;

import com.parkinsoncare.dto.RespuestaAPI;
import com.parkinsoncare.dto.RegistroSintomaDTO;
import com.parkinsoncare.servicio.ServicioSintomas;
import com.parkinsoncare.servicio.ServicioUsuarioAutenticado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sintomas")
@CrossOrigin(origins = "*")
public class ControladorSintomas {

    @Autowired
    private ServicioSintomas servicioSintomas; // ✅ Cambiado a servicioSintomas

    @Autowired
    private ServicioUsuarioAutenticado servicioUsuarioAutenticado;

    @PostMapping("/registrar")
    public RespuestaAPI registrarSintoma(@RequestBody RegistroSintomaRequest request) {
        try {
            // Verificar que el usuario es un paciente
            if (!servicioUsuarioAutenticado.esPaciente()) {
                return RespuestaAPI.error("Solo los pacientes pueden registrar síntomas");
            }

            Long pacienteId = servicioUsuarioAutenticado.obtenerPacienteIdAutenticado();

            // ✅ Usar el nuevo método que crearemos en ServicioSintomas
            RegistroSintomaDTO registro = servicioSintomas.registrarSintoma(
                    pacienteId,
                    request.getNivelTemblor(),
                    request.getNivelRigidez(),
                    request.getNivelBradicinesia(),
                    request.getNivelEquilibrio(),
                    request.getSintomasAdicionales(),
                    request.getNotas()
            );

            return RespuestaAPI.success("Síntoma registrado exitosamente", registro);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al registrar síntoma: " + e.getMessage());
        }
    }

    @GetMapping("/historial")
    public RespuestaAPI obtenerHistorial() {
        try {
            // Verificar que el usuario es un paciente
            if (!servicioUsuarioAutenticado.esPaciente()) {
                return RespuestaAPI.error("Solo los pacientes pueden ver su historial de síntomas");
            }

            Long pacienteId = servicioUsuarioAutenticado.obtenerPacienteIdAutenticado();
            // ✅ Cambiado a RegistroSintomaDTO
            List<RegistroSintomaDTO> historial = servicioSintomas.obtenerHistorialSintomas(pacienteId);
            return RespuestaAPI.success("Historial de síntomas", historial);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener historial: " + e.getMessage());
        }
    }

    @GetMapping("/estadisticas")
    public RespuestaAPI obtenerEstadisticas() {
        try {
            // Verificar que el usuario es un paciente
            if (!servicioUsuarioAutenticado.esPaciente()) {
                return RespuestaAPI.error("Solo los pacientes pueden ver sus estadísticas");
            }

            Long pacienteId = servicioUsuarioAutenticado.obtenerPacienteIdAutenticado();
            // ✅ Usar el nuevo método de estadísticas
            Object estadisticas = servicioSintomas.obtenerEstadisticasSintomas(pacienteId);
            return RespuestaAPI.success("Estadísticas de síntomas", estadisticas);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener estadísticas: " + e.getMessage());
        }
    }

    // Endpoint para doctores que pueden ver síntomas de sus pacientes
    @GetMapping("/paciente/{pacienteId}/historial")
    public RespuestaAPI obtenerHistorialPaciente(@PathVariable Long pacienteId) {
        try {
            // Verificar que el usuario autenticado tiene acceso a este paciente
            if (!servicioUsuarioAutenticado.tieneAccesoAPaciente(pacienteId)) {
                return RespuestaAPI.error("No autorizado para ver síntomas de este paciente");
            }

            List<RegistroSintomaDTO> historial = servicioSintomas.obtenerHistorialSintomas(pacienteId);
            return RespuestaAPI.success("Historial de síntomas del paciente", historial);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener historial: " + e.getMessage());
        }
    }

    // Endpoint para doctores que pueden ver estadísticas de sus pacientes
    @GetMapping("/paciente/{pacienteId}/estadisticas")
    public RespuestaAPI obtenerEstadisticasPaciente(@PathVariable Long pacienteId) {
        try {
            // Verificar que el usuario autenticado tiene acceso a este paciente
            if (!servicioUsuarioAutenticado.tieneAccesoAPaciente(pacienteId)) {
                return RespuestaAPI.error("No autorizado para ver estadísticas de este paciente");
            }

            Object estadisticas = servicioSintomas.obtenerEstadisticasSintomas(pacienteId);
            return RespuestaAPI.success("Estadísticas de síntomas del paciente", estadisticas);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener estadísticas: " + e.getMessage());
        }
    }

    // Clase interna para el request
    public static class RegistroSintomaRequest {
        private Integer nivelTemblor;
        private Integer nivelRigidez;
        private Integer nivelBradicinesia;
        private Integer nivelEquilibrio;
        private String sintomasAdicionales;
        private String notas;

        // Getters y setters
        public Integer getNivelTemblor() { return nivelTemblor; }
        public void setNivelTemblor(Integer nivelTemblor) { this.nivelTemblor = nivelTemblor; }

        public Integer getNivelRigidez() { return nivelRigidez; }
        public void setNivelRigidez(Integer nivelRigidez) { this.nivelRigidez = nivelRigidez; }

        public Integer getNivelBradicinesia() { return nivelBradicinesia; }
        public void setNivelBradicinesia(Integer nivelBradicinesia) { this.nivelBradicinesia = nivelBradicinesia; }

        public Integer getNivelEquilibrio() { return nivelEquilibrio; }
        public void setNivelEquilibrio(Integer nivelEquilibrio) { this.nivelEquilibrio = nivelEquilibrio; }

        public String getSintomasAdicionales() { return sintomasAdicionales; }
        public void setSintomasAdicionales(String sintomasAdicionales) { this.sintomasAdicionales = sintomasAdicionales; }

        public String getNotas() { return notas; }
        public void setNotas(String notas) { this.notas = notas; }

        @Override
        public String toString() {
            return "RegistroSintomaRequest{" +
                    "nivelTemblor=" + nivelTemblor +
                    ", nivelRigidez=" + nivelRigidez +
                    ", nivelBradicinesia=" + nivelBradicinesia +
                    ", nivelEquilibrio=" + nivelEquilibrio +
                    ", sintomasAdicionales='" + sintomasAdicionales + '\'' +
                    ", notas='" + notas + '\'' +
                    '}';
        }
    }
}