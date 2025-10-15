package com.parkinsoncare.servicio;

import com.parkinsoncare.dto.EstadisticasDashboardDTO;
import com.parkinsoncare.modelo.Cita;
import com.parkinsoncare.modelo.Recomendacion;
import com.parkinsoncare.modelo.RegistroSintoma;
import com.parkinsoncare.repositorio.RepositorioCita;
import com.parkinsoncare.repositorio.RepositorioRecomendacion;
import com.parkinsoncare.repositorio.RepositorioRegistroSintoma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ServicioDashboard {

    @Autowired
    private RepositorioCita repositorioCita;

    @Autowired
    private RepositorioRecomendacion repositorioRecomendacion;

    @Autowired
    private RepositorioRegistroSintoma repositorioRegistroSintoma;

    public EstadisticasDashboardDTO obtenerEstadisticasPaciente(Long pacienteId) {
        EstadisticasDashboardDTO estadisticas = new EstadisticasDashboardDTO();

        // Citas del día
        LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
        LocalDateTime finDia = LocalDate.now().atTime(23, 59, 59);
        List<Cita> citasHoy = repositorioCita.findByPacienteId(pacienteId).stream()
                .filter(cita -> !cita.getFechaHora().isBefore(inicioDia) && !cita.getFechaHora().isAfter(finDia))
                .collect(Collectors.toList());

        // Recomendaciones pendientes
        List<Recomendacion> recomendacionesPendientes = repositorioRecomendacion.findByPacienteIdAndCompletada(pacienteId, false);

        // Síntomas recientes (últimos 7 días)
        LocalDateTime inicioSemana = LocalDateTime.now().minusDays(7);
        List<RegistroSintoma> sintomasRecientes = repositorioRegistroSintoma.findByPacienteIdAndFechaRegistroBetween(
                pacienteId, inicioSemana, LocalDateTime.now());

        // Calcular promedios de síntomas
        Map<String, Double> promediosSintomas = calcularPromediosSintomas(sintomasRecientes);

        estadisticas.setCitasHoy(citasHoy.size());
        estadisticas.setRecomendacionesPendientes(recomendacionesPendientes.size());
        estadisticas.setSintomasRegistrados(sintomasRecientes.size());
        estadisticas.setPromediosSintomas(promediosSintomas);

        return estadisticas;
    }

    private Map<String, Double> calcularPromediosSintomas(List<RegistroSintoma> sintomas) {
        return Map.of(
                "temblor", sintomas.stream().mapToInt(RegistroSintoma::getNivelTemblor).average().orElse(0.0),
                "rigidez", sintomas.stream().mapToInt(RegistroSintoma::getNivelRigidez).average().orElse(0.0),
                "bradicinesia", sintomas.stream().mapToInt(RegistroSintoma::getNivelBradicinesia).average().orElse(0.0),
                "equilibrio", sintomas.stream().mapToInt(RegistroSintoma::getNivelEquilibrio).average().orElse(0.0)
        );
    }
}