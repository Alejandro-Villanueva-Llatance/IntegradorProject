package com.parkinsoncare.modelo;

import com.parkinsoncare.modelo.enumeraciones.PrioridadRecomendacion;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recomendaciones")
public class Recomendacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private String titulo;
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private PrioridadRecomendacion prioridad;

    private String categoria; // EJERCICIO, NUTRICION, DESCANSO, etc.
    private boolean completada = false;
    private LocalDateTime fechaCreacion;

    // Constructores
    public Recomendacion() {
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public PrioridadRecomendacion getPrioridad() { return prioridad; }
    public void setPrioridad(PrioridadRecomendacion prioridad) { this.prioridad = prioridad; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public boolean isCompletada() { return completada; }
    public void setCompletada(boolean completada) { this.completada = completada; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}