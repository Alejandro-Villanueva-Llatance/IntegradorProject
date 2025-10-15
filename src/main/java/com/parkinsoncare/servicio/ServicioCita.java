package com.parkinsoncare.servicio;

import com.parkinsoncare.modelo.Cita;
import com.parkinsoncare.modelo.Doctor;
import com.parkinsoncare.modelo.Paciente;
import com.parkinsoncare.modelo.enumeraciones.EstadoCita;
import com.parkinsoncare.repositorio.RepositorioCita;
import com.parkinsoncare.repositorio.RepositorioDoctor;
import com.parkinsoncare.repositorio.RepositorioPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioCita {

    @Autowired
    private RepositorioCita repositorioCita;

    @Autowired
    private RepositorioDoctor repositorioDoctor;

    @Autowired
    private RepositorioPaciente repositorioPaciente;

    public Cita crearCita(Long pacienteId, Long doctorId, LocalDateTime fechaHora, String motivo) {
        Optional<Paciente> paciente = repositorioPaciente.findById(pacienteId);
        Optional<Doctor> doctor = repositorioDoctor.findById(doctorId);

        if (paciente.isEmpty() || doctor.isEmpty()) {
            throw new RuntimeException("Paciente o doctor no encontrado");
        }


        if (!estaDoctorDisponible(doctorId, fechaHora)) {
            throw new RuntimeException("Doctor no disponible en esa fecha/hora");
        }

        Cita cita = new Cita();
        cita.setPaciente(paciente.get());
        cita.setDoctor(doctor.get());
        cita.setFechaHora(fechaHora);
        cita.setMotivo(motivo);
        cita.setEstado(EstadoCita.PROGRAMADA);

        return repositorioCita.save(cita);
    }

    private boolean estaDoctorDisponible(Long doctorId, LocalDateTime fechaHora) {
        LocalDateTime inicio = fechaHora.minusHours(1);
        LocalDateTime fin = fechaHora.plusHours(1);

        List<Cita> citasExistentes = repositorioCita.findByDoctorIdAndFechaHoraBetween(doctorId, inicio, fin);
        return citasExistentes.isEmpty();
    }

    public List<Cita> obtenerCitasPorPaciente(Long pacienteId) {
        return repositorioCita.findByPacienteId(pacienteId);
    }

    public List<Cita> obtenerCitasPorDoctor(Long doctorId) {
        return repositorioCita.findByDoctorId(doctorId);
    }

    public Cita actualizarEstadoCita(Long citaId, EstadoCita nuevoEstado) {
        Optional<Cita> citaOpt = repositorioCita.findById(citaId);
        if (citaOpt.isPresent()) {
            Cita cita = citaOpt.get();
            cita.setEstado(nuevoEstado);
            return repositorioCita.save(cita);
        }
        throw new RuntimeException("Cita no encontrada");
    }
}