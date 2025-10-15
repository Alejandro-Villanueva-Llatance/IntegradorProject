package com.parkinsoncare.controlador;

import com.parkinsoncare.dto.RespuestaAPI;
import com.parkinsoncare.modelo.Cita;
import com.parkinsoncare.modelo.enumeraciones.EstadoCita;
import com.parkinsoncare.servicio.ServicioCita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class ControladorCita {

    @Autowired
    private ServicioCita servicioCita;

    @PostMapping
    public RespuestaAPI crearCita(@RequestBody SolicitudCita solicitud) {
        try {
            Cita cita = servicioCita.crearCita(
                    solicitud.getPacienteId(),
                    solicitud.getDoctorId(),
                    solicitud.getFechaHora(),
                    solicitud.getMotivo()
            );
            return RespuestaAPI.success("Cita creada exitosamente", cita);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al crear cita: " + e.getMessage());
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    public RespuestaAPI obtenerCitasPaciente(@PathVariable Long pacienteId) {
        try {
            List<Cita> citas = servicioCita.obtenerCitasPorPaciente(pacienteId);
            return RespuestaAPI.success("Citas del paciente", citas);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener citas: " + e.getMessage());
        }
    }

    @GetMapping("/doctor/{doctorId}")
    public RespuestaAPI obtenerCitasDoctor(@PathVariable Long doctorId) {
        try {
            List<Cita> citas = servicioCita.obtenerCitasPorDoctor(doctorId);
            return RespuestaAPI.success("Citas del doctor", citas);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener citas: " + e.getMessage());
        }
    }

    @PutMapping("/{citaId}/estado")
    public RespuestaAPI actualizarEstadoCita(@PathVariable Long citaId, @RequestBody ActualizarEstadoRequest request) {
        try {
            Cita cita = servicioCita.actualizarEstadoCita(citaId, request.getNuevoEstado());
            return RespuestaAPI.success("Estado de cita actualizado", cita);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al actualizar cita: " + e.getMessage());
        }
    }


    public static class SolicitudCita {
        private Long pacienteId;
        private Long doctorId;
        private LocalDateTime fechaHora;
        private String motivo;


        public Long getPacienteId() { return pacienteId; }
        public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
        public Long getDoctorId() { return doctorId; }
        public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
        public LocalDateTime getFechaHora() { return fechaHora; }
        public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
        public String getMotivo() { return motivo; }
        public void setMotivo(String motivo) { this.motivo = motivo; }
    }

    public static class ActualizarEstadoRequest {
        private EstadoCita nuevoEstado;


        public EstadoCita getNuevoEstado() { return nuevoEstado; }
        public void setNuevoEstado(EstadoCita nuevoEstado) { this.nuevoEstado = nuevoEstado; }
    }
}