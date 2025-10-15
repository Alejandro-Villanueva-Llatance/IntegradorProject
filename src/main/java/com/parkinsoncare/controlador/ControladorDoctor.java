package com.parkinsoncare.controlador;

import com.parkinsoncare.dto.RespuestaAPI;
import com.parkinsoncare.modelo.Doctor;
import com.parkinsoncare.modelo.Paciente;
import com.parkinsoncare.repositorio.RepositorioDoctor;
import com.parkinsoncare.repositorio.RepositorioPaciente;
import com.parkinsoncare.servicio.ServicioUsuarioAutenticado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/doctores")
@CrossOrigin(origins = "*")
public class ControladorDoctor {

    @Autowired
    private ServicioUsuarioAutenticado servicioUsuarioAutenticado;

    @Autowired
    private RepositorioDoctor repositorioDoctor;

    @Autowired
    private RepositorioPaciente repositorioPaciente;

    @GetMapping("/dashboard")
    public RespuestaAPI obtenerDashboard() {
        try {

            if (!servicioUsuarioAutenticado.esDoctor()) {
                return RespuestaAPI.error("Acceso denegado: Solo los doctores pueden acceder a este recurso");
            }


            Long usuarioId = servicioUsuarioAutenticado.obtenerUsuarioIdAutenticado();
            Optional<Doctor> doctorOpt = repositorioDoctor.findById(usuarioId);

            if (doctorOpt.isEmpty()) {
                return RespuestaAPI.error("Doctor no encontrado");
            }

            Doctor doctor = doctorOpt.get();


            List<Paciente> pacientesAsignados = repositorioPaciente.findByDoctorAsignadoId(doctor.getId());

            Map<String, Object> dashboardData = new HashMap<>();
            dashboardData.put("totalPacientes", pacientesAsignados.size());
            dashboardData.put("citasHoy", 0); // Por implementar
            dashboardData.put("alertasCriticas", 0); // Por implementar
            dashboardData.put("doctor", doctor.getNombre());
            dashboardData.put("doctorId", doctor.getId());

            return RespuestaAPI.success("Dashboard del doctor", dashboardData);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al cargar dashboard: " + e.getMessage());
        }
    }

    @GetMapping("/pacientes")
    public RespuestaAPI obtenerPacientes() {
        try {

            if (!servicioUsuarioAutenticado.esDoctor()) {
                return RespuestaAPI.error("Acceso denegado: Solo los doctores pueden acceder a este recurso");
            }


            Long usuarioId = servicioUsuarioAutenticado.obtenerUsuarioIdAutenticado();
            Optional<Doctor> doctorOpt = repositorioDoctor.findById(usuarioId);

            if (doctorOpt.isEmpty()) {
                return RespuestaAPI.error("Doctor no encontrado");
            }

            Doctor doctor = doctorOpt.get();


            List<Paciente> pacientes = repositorioPaciente.findByDoctorAsignadoId(doctor.getId());


            List<Map<String, Object>> pacientesData = pacientes.stream()
                    .map(paciente -> {
                        Map<String, Object> data = new HashMap<>();
                        data.put("id", paciente.getId());
                        data.put("nombre", paciente.getNombre());
                        data.put("edad", paciente.getEdad());
                        data.put("genero", paciente.getGenero());
                        data.put("etapaParkinson", paciente.getEtapaParkinson() != null ?
                                paciente.getEtapaParkinson().name() : "NO_ESPECIFICADA");
                        data.put("email", paciente.getEmail());
                        data.put("telefono", paciente.getTelefono());
                        data.put("fechaDiagnostico", paciente.getFechaDiagnostico());
                        return data;
                    })
                    .collect(Collectors.toList());

            return RespuestaAPI.success("Pacientes del doctor", pacientesData);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener pacientes: " + e.getMessage());
        }
    }

    @GetMapping("/pacientes/{pacienteId}")
    public RespuestaAPI obtenerDetallesPaciente(@PathVariable Long pacienteId) {
        try {

            if (!servicioUsuarioAutenticado.esDoctor()) {
                return RespuestaAPI.error("Acceso denegado: Solo los doctores pueden acceder a este recurso");
            }


            if (!servicioUsuarioAutenticado.tieneAccesoAPaciente(pacienteId)) {
                return RespuestaAPI.error("No autorizado para ver este paciente");
            }

            Optional<Paciente> pacienteOpt = repositorioPaciente.findById(pacienteId);

            if (pacienteOpt.isEmpty()) {
                return RespuestaAPI.error("Paciente no encontrado");
            }

            Paciente paciente = pacienteOpt.get();

            Map<String, Object> pacienteData = new HashMap<>();
            pacienteData.put("id", paciente.getId());
            pacienteData.put("nombre", paciente.getNombre());
            pacienteData.put("edad", paciente.getEdad());
            pacienteData.put("genero", paciente.getGenero());
            pacienteData.put("etapaParkinson", paciente.getEtapaParkinson() != null ?
                    paciente.getEtapaParkinson().name() : "NO_ESPECIFICADA");
            pacienteData.put("email", paciente.getEmail());
            pacienteData.put("telefono", paciente.getTelefono());
            pacienteData.put("fechaDiagnostico", paciente.getFechaDiagnostico());
            pacienteData.put("medicamentosActuales", paciente.getMedicamentosActuales());
            pacienteData.put("comorbilidades", paciente.getComorbilidades());

            return RespuestaAPI.success("Detalles del paciente", pacienteData);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener paciente: " + e.getMessage());
        }
    }

    @GetMapping("/perfil")
    public RespuestaAPI obtenerPerfil() {
        try {

            if (!servicioUsuarioAutenticado.esDoctor()) {
                return RespuestaAPI.error("Acceso denegado: Solo los doctores pueden acceder a este recurso");
            }

            Long usuarioId = servicioUsuarioAutenticado.obtenerUsuarioIdAutenticado();
            Optional<Doctor> doctorOpt = repositorioDoctor.findById(usuarioId);

            if (doctorOpt.isEmpty()) {
                return RespuestaAPI.error("Doctor no encontrado");
            }

            Doctor doctor = doctorOpt.get();

            Map<String, Object> perfilData = new HashMap<>();
            perfilData.put("id", doctor.getId());
            perfilData.put("nombre", doctor.getNombre());
            perfilData.put("email", doctor.getEmail());
            perfilData.put("telefono", doctor.getTelefono());
            perfilData.put("especialidad", doctor.getEspecialidad());
            perfilData.put("numeroLicencia", doctor.getNumeroLicencia());
            perfilData.put("username", doctor.getUsername());

            return RespuestaAPI.success("Perfil del doctor", perfilData);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener perfil: " + e.getMessage());
        }
    }

    @GetMapping("/citas")
    public RespuestaAPI obtenerCitas() {
        try {

            if (!servicioUsuarioAutenticado.esDoctor()) {
                return RespuestaAPI.error("Acceso denegado: Solo los doctores pueden acceder a este recurso");
            }


            Map<String, Object> citasData = new HashMap<>();
            citasData.put("citasHoy", 0);
            citasData.put("citasPendientes", 0);
            citasData.put("proximaCita", null);

            return RespuestaAPI.success("Citas del doctor", citasData);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener citas: " + e.getMessage());
        }
    }

    @GetMapping("/estadisticas")
    public RespuestaAPI obtenerEstadisticas() {
        try {

            if (!servicioUsuarioAutenticado.esDoctor()) {
                return RespuestaAPI.error("Acceso denegado: Solo los doctores pueden acceder a este recurso");
            }

            Long usuarioId = servicioUsuarioAutenticado.obtenerUsuarioIdAutenticado();
            Optional<Doctor> doctorOpt = repositorioDoctor.findById(usuarioId);

            if (doctorOpt.isEmpty()) {
                return RespuestaAPI.error("Doctor no encontrado");
            }

            Doctor doctor = doctorOpt.get();
            List<Paciente> pacientes = repositorioPaciente.findByDoctorAsignadoId(doctor.getId());

            Map<String, Object> estadisticas = new HashMap<>();
            estadisticas.put("totalPacientes", pacientes.size());
            estadisticas.put("pacientesPorEtapa", calcularPacientesPorEtapa(pacientes));

            estadisticas.put("doctor", doctor.getNombre());

            return RespuestaAPI.success("Estadísticas del doctor", estadisticas);
        } catch (Exception e) {
            return RespuestaAPI.error("Error al obtener estadísticas: " + e.getMessage());
        }
    }

    private Map<String, Long> calcularPacientesPorEtapa(List<Paciente> pacientes) {
        return pacientes.stream()
                .collect(Collectors.groupingBy(
                        paciente -> paciente.getEtapaParkinson() != null ?
                                paciente.getEtapaParkinson().name() : "NO_ESPECIFICADA",
                        Collectors.counting()
                ));
    }


}