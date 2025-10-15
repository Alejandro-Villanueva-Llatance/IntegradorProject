package com.parkinsoncare.repositorio;

import com.parkinsoncare.modelo.RegistroSintoma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RepositorioRegistroSintoma extends JpaRepository<RegistroSintoma, Long> {
    List<RegistroSintoma> findByPacienteIdOrderByFechaRegistroDesc(Long pacienteId);
    List<RegistroSintoma> findByPacienteIdAndFechaRegistroBetween(Long pacienteId, LocalDateTime inicio, LocalDateTime fin);
}