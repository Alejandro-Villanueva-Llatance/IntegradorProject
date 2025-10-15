package com.parkinsoncare.repositorio;

import com.parkinsoncare.modelo.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RepositorioCita extends JpaRepository<Cita, Long> {
    List<Cita> findByPacienteId(Long pacienteId);
    List<Cita> findByDoctorId(Long doctorId);
    List<Cita> findByDoctorIdAndFechaHoraBetween(Long doctorId, LocalDateTime inicio, LocalDateTime fin);
}