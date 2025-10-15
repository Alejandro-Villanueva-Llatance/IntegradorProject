package com.parkinsoncare.repositorio;

import com.parkinsoncare.modelo.Recomendacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioRecomendacion extends JpaRepository<Recomendacion, Long> {
    List<Recomendacion> findByPacienteId(Long pacienteId);
    List<Recomendacion> findByPacienteIdAndCompletada(Long pacienteId, boolean completada);
    List<Recomendacion> findByPacienteIdAndPrioridad(Long pacienteId, String prioridad);
    List<Recomendacion> findByPacienteIdAndCategoria(Long pacienteId, String categoria);
}