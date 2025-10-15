package com.parkinsoncare.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "doctores")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Doctor extends Usuario {

    @Column(name = "especialidad")
    private String especialidad;

    @Column(name = "numero_licencia", unique = true)
    private String numeroLicencia;

    private String telefono;
    private String direccionConsultorio;
    private String horarioAtencion;

    // Constructores
    public Doctor() {
        super();
    }

    public Doctor(String username, String password, String email, String nombre,
                  String especialidad, String numeroLicencia) {
        super(username, password, email, nombre, com.parkinsoncare.modelo.enumeraciones.RolUsuario.DOCTOR);
        this.especialidad = especialidad;
        this.numeroLicencia = numeroLicencia;
    }


    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getNumeroLicencia() { return numeroLicencia; }
    public void setNumeroLicencia(String numeroLicencia) { this.numeroLicencia = numeroLicencia; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccionConsultorio() { return direccionConsultorio; }
    public void setDireccionConsultorio(String direccionConsultorio) { this.direccionConsultorio = direccionConsultorio; }

    public String getHorarioAtencion() { return horarioAtencion; }
    public void setHorarioAtencion(String horarioAtencion) { this.horarioAtencion = horarioAtencion; }
}