package com.parkinsoncare.servicio;

import com.parkinsoncare.modelo.Paciente;
import com.parkinsoncare.modelo.Recomendacion;
import com.parkinsoncare.modelo.enumeraciones.EtapaParkinson;
import com.parkinsoncare.modelo.enumeraciones.PrioridadRecomendacion;
import com.parkinsoncare.repositorio.RepositorioPaciente;
import com.parkinsoncare.repositorio.RepositorioRecomendacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioRecomendacion {

    @Autowired
    private RepositorioRecomendacion repositorioRecomendacion;

    @Autowired
    private RepositorioPaciente repositorioPaciente;

    @Autowired
    private ServicioIA servicioIA;



    private void limpiarRecomendacionesAnteriores(Long pacienteId) {
        List<Recomendacion> recomendacionesAnteriores = repositorioRecomendacion.findByPacienteIdAndCompletada(pacienteId, false);
        for (Recomendacion rec : recomendacionesAnteriores) {
            repositorioRecomendacion.delete(rec);
        }
    }
    private List<Recomendacion> generarRecomendacionesEsenciales() {
        List<Recomendacion> recomendaciones = new ArrayList<>();

        // Recomendaciones que TODOS los pacientes deben tener
        recomendaciones.add(crearRecomendacion(
                "Seguimiento médico regular",
                "Mantener citas periódicas con el neurólogo para ajustar tratamiento",
                PrioridadRecomendacion.URGENTE,
                "TERAPIA"
        ));

        recomendaciones.add(crearRecomendacion(
                "Adherencia medicamentosa",
                "Tomar la medicación exactamente como fue recetada, sin saltar dosis",
                PrioridadRecomendacion.URGENTE,
                "CONSEJO_DIARIO"
        ));

        recomendaciones.add(crearRecomendacion(
                "Comunicación de síntomas nuevos",
                "Informar inmediatamente al médico sobre cualquier síntoma nuevo o cambio en síntomas existentes",
                PrioridadRecomendacion.ALTA,
                "TERAPIA"
        ));

        return recomendaciones;
    }



    // ✅ MÉTODO ACTUALIZADO CON LA LÓGICA COMPLETA:
    public List<Recomendacion> generarRecomendacionesPersonalizadas(Long pacienteId) {

        Optional<Paciente> pacienteOpt = repositorioPaciente.findById(pacienteId);
        if (pacienteOpt.isEmpty()) {
            throw new RuntimeException("Paciente no encontrado");
        }

        Paciente paciente = pacienteOpt.get();
        List<Recomendacion> recomendaciones = new ArrayList<>();

        // Limpiar recomendaciones anteriores no completadas
        limpiarRecomendacionesAnteriores(pacienteId);

        // 1. Primero, intentar con IA si está disponible
        boolean iaDisponible = servicioIA.verificarConexionIA();

        if (iaDisponible) {
            try {
                System.out.println("🚀 Generando recomendaciones con IA para paciente: " + paciente.getNombre());
                List<Recomendacion> recomendacionesIA = servicioIA.generarRecomendacionesIA(paciente);
                recomendaciones.addAll(recomendacionesIA);
                System.out.println("✅ " + recomendacionesIA.size() + " recomendaciones generadas por IA");
            } catch (Exception e) {
                System.out.println("❌ Falló IA, usando reglas de negocio: " + e.getMessage());
                // Fallback a reglas de negocio
                recomendaciones.addAll(generarRecomendacionesPorEtapa(paciente.getEtapaParkinson()));
            }
        } else {
            System.out.println("ℹ️ IA no configurada, usando reglas de negocio para: " + paciente.getNombre());
            recomendaciones.addAll(generarRecomendacionesPorEtapa(paciente.getEtapaParkinson()));
        }

        // 2. Agregar recomendaciones predefinidas (que SÍ tienes)
        recomendaciones.addAll(generarRecomendacionesPredefinidas());

        // 3. Agregar recomendaciones esenciales (nuevo método)
        recomendaciones.addAll(generarRecomendacionesEsenciales());

        // Guardar recomendaciones
        for (Recomendacion rec : recomendaciones) {
            rec.setPaciente(paciente);
            rec.setFechaCreacion(LocalDateTime.now());
            repositorioRecomendacion.save(rec);
        }

        System.out.println("📊 Total recomendaciones generadas para " + paciente.getNombre() + ": " + recomendaciones.size());
        return recomendaciones;
    }

    private List<Recomendacion> generarRecomendacionesPorEtapa(EtapaParkinson etapa) {
        List<Recomendacion> recomendaciones = new ArrayList<>();

        if (etapa == null) {
            etapa = EtapaParkinson.ETAPA_1; // Valor por defecto
        }

        switch (etapa) {
            case ETAPA_1:
                recomendaciones.add(crearRecomendacion(
                        "Ejercicios de equilibrio básicos",
                        "Practicar equilibrio estático 10 minutos al día",
                        PrioridadRecomendacion.MEDIA,
                        "EJERCICIO"
                ));
                recomendaciones.add(crearRecomendacion(
                        "Caminatas diarias",
                        "Caminar 20-30 minutos al día en terreno plano",
                        PrioridadRecomendacion.MEDIA,
                        "EJERCICIO"
                ));
                break;

            case ETAPA_2:
                recomendaciones.add(crearRecomendacion(
                        "Terapia física moderada",
                        "Sesiones de terapia física 3 veces por semana",
                        PrioridadRecomendacion.ALTA,
                        "EJERCICIO"
                ));
                recomendaciones.add(crearRecomendacion(
                        "Ejercicios de fortalecimiento",
                        "Realizar ejercicios con bandas elásticas 2 veces por semana",
                        PrioridadRecomendacion.MEDIA,
                        "EJERCICIO"
                ));
                break;

            case ETAPA_3:
                recomendaciones.add(crearRecomendacion(
                        "Terapia física intensiva",
                        "Sesiones de terapia supervisada 4 veces por semana",
                        PrioridadRecomendacion.ALTA,
                        "EJERCICIO"
                ));
                recomendaciones.add(crearRecomendacion(
                        "Uso de asistencia para caminar",
                        "Utilizar andador o bastón para mayor estabilidad",
                        PrioridadRecomendacion.ALTA,
                        "CONSEJO_DIARIO"
                ));
                break;

            case ETAPA_4:
            case ETAPA_5:
                recomendaciones.add(crearRecomendacion(
                        "Ejercicios en silla",
                        "Realizar ejercicios de movilidad sentado 15 minutos al día",
                        PrioridadRecomendacion.ALTA,
                        "EJERCICIO"
                ));
                recomendaciones.add(crearRecomendacion(
                        "Asistencia para actividades diarias",
                        "Solicitar ayuda para tareas que requieran equilibrio",
                        PrioridadRecomendacion.URGENTE,
                        "CONSEJO_DIARIO"
                ));
                break;
        }

        return recomendaciones;
    }

    private List<Recomendacion> generarRecomendacionesPredefinidas() {
        List<Recomendacion> recomendaciones = new ArrayList<>();

        // Nutrición
        recomendaciones.add(crearRecomendacion(
                "Hidratación adecuada",
                "Beber 2 litros de agua al día",
                PrioridadRecomendacion.MEDIA,
                "NUTRICION"
        ));
        recomendaciones.add(crearRecomendacion(
                "Dieta rica en fibra",
                "Consumir frutas, verduras y cereales integrales para prevenir estreñimiento",
                PrioridadRecomendacion.MEDIA,
                "NUTRICION"
        ));

        // Descanso
        recomendaciones.add(crearRecomendacion(
                "Descanso regular",
                "Dormir 7-8 horas diarias",
                PrioridadRecomendacion.ALTA,
                "DESCANSO"
        ));
        recomendaciones.add(crearRecomendacion(
                "Rutina de sueño",
                "Mantener horarios consistentes para dormir y despertar",
                PrioridadRecomendacion.MEDIA,
                "DESCANSO"
        ));

        // Consejos diarios
        recomendaciones.add(crearRecomendacion(
                "Tomar medicación a tiempo",
                "Seguir estrictamente el horario de medicación recetado",
                PrioridadRecomendacion.URGENTE,
                "CONSEJO_DIARIO"
        ));
        recomendaciones.add(crearRecomendacion(
                "Evitar superficies resbaladizas",
                "Usir alfombras antideslizantes en baño y duchas",
                PrioridadRecomendacion.ALTA,
                "CONSEJO_DIARIO"
        ));

        return recomendaciones;
    }


    private Recomendacion crearRecomendacion(String titulo, String descripcion,
                                             PrioridadRecomendacion prioridad, String categoria) {
        Recomendacion rec = new Recomendacion();
        rec.setTitulo(titulo);
        rec.setDescripcion(descripcion);
        rec.setPrioridad(prioridad);
        rec.setCategoria(categoria);
        rec.setCompletada(false);
        return rec;
    }

    public List<Recomendacion> obtenerRecomendacionesPaciente(Long pacienteId) {
        return repositorioRecomendacion.findByPacienteId(pacienteId);
    }

    public List<Recomendacion> obtenerRecomendacionesPendientes(Long pacienteId) {
        return repositorioRecomendacion.findByPacienteIdAndCompletada(pacienteId, false);
    }

    public Recomendacion marcarComoCompletada(Long recomendacionId) {
        Optional<Recomendacion> recomendacionOpt = repositorioRecomendacion.findById(recomendacionId);
        if (recomendacionOpt.isPresent()) {
            Recomendacion recomendacion = recomendacionOpt.get();
            recomendacion.setCompletada(true);
            return repositorioRecomendacion.save(recomendacion);
        }
        throw new RuntimeException("Recomendación no encontrada");
    }

    public List<Recomendacion> obtenerRecomendacionesPorCategoria(Long pacienteId, String categoria) {
        return repositorioRecomendacion.findByPacienteIdAndCategoria(pacienteId, categoria);
    }

    public List<Recomendacion> obtenerRecomendacionesPorPrioridad(Long pacienteId, String prioridad) {
        return repositorioRecomendacion.findByPacienteIdAndPrioridad(pacienteId, prioridad);
    }
}