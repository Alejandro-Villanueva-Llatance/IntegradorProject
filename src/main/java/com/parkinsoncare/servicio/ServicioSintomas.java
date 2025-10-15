package com.parkinsoncare.servicio;

import com.parkinsoncare.dto.RegistroSintomaDTO;
import com.parkinsoncare.modelo.Paciente;
import com.parkinsoncare.modelo.RegistroSintoma;
import com.parkinsoncare.repositorio.RepositorioPaciente;
import com.parkinsoncare.repositorio.RepositorioRegistroSintoma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicioSintomas {

    @Autowired
    private RepositorioRegistroSintoma repositorioRegistroSintoma;

    @Autowired
    private RepositorioPaciente repositorioPaciente;

    // ✅ NUEVO MÉTODO: Registrar síntomas con parámetros individuales
    public RegistroSintomaDTO registrarSintoma(Long pacienteId, Integer nivelTemblor, Integer nivelRigidez,
                                               Integer nivelBradicinesia, Integer nivelEquilibrio,
                                               String sintomasAdicionales, String notas) {

        // Validar datos
        validarNivelesSintomas(nivelTemblor, nivelRigidez, nivelBradicinesia, nivelEquilibrio);

        Optional<Paciente> paciente = repositorioPaciente.findById(pacienteId);
        if (paciente.isEmpty()) {
            throw new RuntimeException("Paciente no encontrado");
        }

        RegistroSintoma registro = new RegistroSintoma();
        registro.setPaciente(paciente.get());
        registro.setNivelTemblor(nivelTemblor);
        registro.setNivelRigidez(nivelRigidez);
        registro.setNivelBradicinesia(nivelBradicinesia);
        registro.setNivelEquilibrio(nivelEquilibrio);
        registro.setSintomasAdicionales(sintomasAdicionales);
        registro.setNotas(notas);
        registro.setFechaRegistro(LocalDateTime.now());

        RegistroSintoma guardado = repositorioRegistroSintoma.save(registro);
        return convertirADTO(guardado);
    }

    // ✅ MÉTODO ORIGINAL (mantener para compatibilidad)
    public RegistroSintoma registrarSintoma(Long pacienteId, RegistroSintoma registro) {
        Optional<Paciente> paciente = repositorioPaciente.findById(pacienteId);
        if (paciente.isEmpty()) {
            throw new RuntimeException("Paciente no encontrado");
        }

        registro.setPaciente(paciente.get());
        registro.setFechaRegistro(LocalDateTime.now());

        return repositorioRegistroSintoma.save(registro);
    }

    // ✅ NUEVO MÉTODO: Obtener historial como DTO
    public List<RegistroSintomaDTO> obtenerHistorialSintomas(Long pacienteId) {
        List<RegistroSintoma> registros = repositorioRegistroSintoma.findByPacienteIdOrderByFechaRegistroDesc(pacienteId);
        return registros.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // ✅ MÉTODO ORIGINAL (mantener)
    public List<RegistroSintoma> obtenerHistorialSintomasEntidades(Long pacienteId) {
        return repositorioRegistroSintoma.findByPacienteIdOrderByFechaRegistroDesc(pacienteId);
    }

    // ✅ NUEVO MÉTODO: Estadísticas de síntomas
    public Object obtenerEstadisticasSintomas(Long pacienteId) {
        List<RegistroSintoma> registros = obtenerHistorialSintomasEntidades(pacienteId);

        if (registros.isEmpty()) {
            return new EstadisticasSintomas(0, 0, 0, 0, 0, null, "SIN DATOS");
        }

        // Calcular promedios
        double avgTemblor = registros.stream().mapToInt(RegistroSintoma::getNivelTemblor).average().orElse(0.0);
        double avgRigidez = registros.stream().mapToInt(RegistroSintoma::getNivelRigidez).average().orElse(0.0);
        double avgBradicinesia = registros.stream().mapToInt(RegistroSintoma::getNivelBradicinesia).average().orElse(0.0);
        double avgEquilibrio = registros.stream().mapToInt(RegistroSintoma::getNivelEquilibrio).average().orElse(0.0);

        // Obtener el último registro
        RegistroSintoma ultimoRegistro = registros.get(0);

        return new EstadisticasSintomas(
                Math.round(avgTemblor * 100.0) / 100.0,
                Math.round(avgRigidez * 100.0) / 100.0,
                Math.round(avgBradicinesia * 100.0) / 100.0,
                Math.round(avgEquilibrio * 100.0) / 100.0,
                registros.size(),
                ultimoRegistro.getFechaRegistro(),
                calcularTendencia(registros)
        );
    }

    // ✅ MÉTODOS AUXILIARES
    private void validarNivelesSintomas(Integer... niveles) {
        for (Integer nivel : niveles) {
            if (nivel == null || nivel < 1 || nivel > 10) {
                throw new RuntimeException("Los niveles de síntomas deben estar entre 1 y 10");
            }
        }
    }

    private String calcularTendencia(List<RegistroSintoma> registros) {
        if (registros.size() < 2) {
            return "ESTABLE";
        }

        // Comparar los últimos 2 registros
        RegistroSintoma ultimo = registros.get(0);
        RegistroSintoma anterior = registros.get(1);

        int sumaUltimo = ultimo.getNivelTemblor() + ultimo.getNivelRigidez() +
                ultimo.getNivelBradicinesia() + ultimo.getNivelEquilibrio();
        int sumaAnterior = anterior.getNivelTemblor() + anterior.getNivelRigidez() +
                anterior.getNivelBradicinesia() + anterior.getNivelEquilibrio();

        if (sumaUltimo > sumaAnterior + 4) return "EMPEORANDO";
        if (sumaUltimo < sumaAnterior - 4) return "MEJORANDO";
        return "ESTABLE";
    }

    private RegistroSintomaDTO convertirADTO(RegistroSintoma registro) {
        RegistroSintomaDTO dto = new RegistroSintomaDTO();
        dto.setId(registro.getId());
        dto.setPacienteId(registro.getPaciente().getId());
        dto.setFechaRegistro(registro.getFechaRegistro());
        dto.setNivelTemblor(registro.getNivelTemblor());
        dto.setNivelRigidez(registro.getNivelRigidez());
        dto.setNivelBradicinesia(registro.getNivelBradicinesia());
        dto.setNivelEquilibrio(registro.getNivelEquilibrio());
        dto.setSintomasAdicionales(registro.getSintomasAdicionales());
        dto.setNotas(registro.getNotas());
        return dto;
    }

    // ✅ MÉTODOS EXISTENTES (mantener)
    public List<RegistroSintoma> obtenerSintomasPorRangoFechas(Long pacienteId, LocalDateTime inicio, LocalDateTime fin) {
        return repositorioRegistroSintoma.findByPacienteIdAndFechaRegistroBetween(pacienteId, inicio, fin);
    }

    public Double calcularPromedioSintoma(Long pacienteId, String tipoSintoma) {
        List<RegistroSintoma> registros = obtenerHistorialSintomasEntidades(pacienteId);

        return registros.stream()
                .mapToInt(registro -> {
                    switch (tipoSintoma.toUpperCase()) {
                        case "TEMBLOR": return registro.getNivelTemblor();
                        case "RIGIDEZ": return registro.getNivelRigidez();
                        case "BRADICINESIA": return registro.getNivelBradicinesia();
                        case "EQUILIBRIO": return registro.getNivelEquilibrio();
                        default: return 0;
                    }
                })
                .average()
                .orElse(0.0);
    }

    // ✅ CLASE INTERNA PARA ESTADÍSTICAS
    public static class EstadisticasSintomas {
        public final double promedioTemblor;
        public final double promedioRigidez;
        public final double promedioBradicinesia;
        public final double promedioEquilibrio;
        public final int totalRegistros;
        public final LocalDateTime ultimoRegistro;
        public final String tendencia;

        public EstadisticasSintomas(double promedioTemblor, double promedioRigidez,
                                    double promedioBradicinesia, double promedioEquilibrio,
                                    int totalRegistros, LocalDateTime ultimoRegistro, String tendencia) {
            this.promedioTemblor = promedioTemblor;
            this.promedioRigidez = promedioRigidez;
            this.promedioBradicinesia = promedioBradicinesia;
            this.promedioEquilibrio = promedioEquilibrio;
            this.totalRegistros = totalRegistros;
            this.ultimoRegistro = ultimoRegistro;
            this.tendencia = tendencia;
        }
    }
}