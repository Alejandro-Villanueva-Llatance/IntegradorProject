package com.parkinsoncare.modelo;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medicaciones")
public class Medicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private String nombreMedicamento;
    private String dosis;
    private String frecuencia;

    @ElementCollection
    @CollectionTable(name = "medicacion_horarios", joinColumns = @JoinColumn(name = "medicacion_id"))
    @Column(name = "horario")
    private List<LocalTime> horarios = new ArrayList<>();

    private String instrucciones;
    private boolean activa = true;

    // Constructores, getters y setters
    public Medicacion() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public String getNombreMedicamento() { return nombreMedicamento; }
    public void setNombreMedicamento(String nombreMedicamento) { this.nombreMedicamento = nombreMedicamento; }
    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }
    public String getFrecuencia() { return frecuencia; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }
    public List<LocalTime> getHorarios() { return horarios; }
    public void setHorarios(List<LocalTime> horarios) { this.horarios = horarios; }
    public String getInstrucciones() { return instrucciones; }
    public void setInstrucciones(String instrucciones) { this.instrucciones = instrucciones; }
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
}