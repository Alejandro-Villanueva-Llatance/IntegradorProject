package com.parkinsoncare.dto;

import java.time.LocalDateTime;

public class RegistroSintomaDTO {
    private Long id;
    private Long pacienteId;
    private LocalDateTime fechaRegistro;
    private Integer nivelTemblor;
    private Integer nivelRigidez;
    private Integer nivelBradicinesia;
    private Integer nivelEquilibrio;
    private String sintomasAdicionales;
    private String notas;


    public RegistroSintomaDTO() {}


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
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