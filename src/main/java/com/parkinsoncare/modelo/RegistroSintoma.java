package com.parkinsoncare.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registros_sintomas")
public class RegistroSintoma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime fechaRegistro;
    private Integer nivelTemblor; // 1-10
    private Integer nivelRigidez; // 1-10
    private Integer nivelBradicinesia; // 1-10
    private Integer nivelEquilibrio; // 1-10
    private String sintomasAdicionales;
    private String notas;

    // Constructores
    public RegistroSintoma() {
        this.fechaRegistro = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public Integer getNivelTemblor() { return nivelTemblor; }
    public void setNivelTemblor(Integer nivelTemblor) { this.nivelTemblor = nivelTemblor; }

    public Integer getNivelRigidez() { return nivelRigidez; }
    public void setNivelRigidez(Integer nivelRigidez) { this.nivelRigidez = nivelRigidez; }

    public Integer getNivelBradicinesia() { return nivelBradicinesia; }
    public void setNivelBradicinesia(Integer nivelBradicinesia) { this.nivelBradicinesia = nivelBradicinesia; }

    public Integer getNivelEquilibrio() { return nivelEquilibrio; }
    public void setNivelEquilibrio(Integer nivelEquilibrio) { this.nivelEquilibrio = nivelEquilibrio; }

    public String getSintomasAdicionales() { return sintomasAdicionales; }
    public void setSintomasAdicionales(String sintomasAdicionales) { this.sintomasAdicionales = sintomasAdicionales; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}