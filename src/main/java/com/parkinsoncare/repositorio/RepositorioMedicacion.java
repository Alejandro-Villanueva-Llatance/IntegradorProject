package com.parkinsoncare.repositorio;

import com.parkinsoncare.modelo.Medicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioMedicacion extends JpaRepository<Medicacion, Long> {
    List<Medicacion> findByPacienteId(Long pacienteId);
    List<Medicacion> findByPacienteIdAndActiva(Long pacienteId, boolean activa);
}