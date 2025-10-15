package com.parkinsoncare.repositorio;

import com.parkinsoncare.modelo.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioPaciente extends JpaRepository<Paciente, Long> {

    // Buscar pacientes por doctor asignado
    @Query("SELECT p FROM Paciente p WHERE p.doctorAsignado.id = :doctorId")
    List<Paciente> findByDoctorAsignadoId(@Param("doctorId") Long doctorId);

    Optional<Paciente> findByUsername(String username);
}