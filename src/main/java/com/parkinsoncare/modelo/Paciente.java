package com.parkinsoncare.modelo;

import com.parkinsoncare.modelo.enumeraciones.EtapaParkinson;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pacientes")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Paciente extends Usuario {

    private LocalDate fechaNacimiento;
    private String genero;
    private String direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_asignado_id")
    private Doctor doctorAsignado;

    @Enumerated(EnumType.STRING)
    private EtapaParkinson etapaParkinson;

    private String historialMedico;
    private String medicamentosActuales;
    private String contactoEmergencia;
    private String telefonoEmergencia;
    private String edad;

    // ✅ CAMPOS NUEVOS QUE FALTABAN
    private LocalDate fechaDiagnostico;
    private String comorbilidades;
    private String telefono;
    private String alergias;
    private String antecedentesFamiliares;
    private String observaciones;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    // Constructores
    public Paciente() {
        super();
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    public Paciente(String username, String password, String email, String nombre,
                    LocalDate fechaNacimiento, String genero, EtapaParkinson etapaParkinson) {
        super(username, password, email, nombre, com.parkinsoncare.modelo.enumeraciones.RolUsuario.PACIENTE);
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.etapaParkinson = etapaParkinson;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    // Getters y Setters
    public Doctor getDoctorAsignado() {
        return doctorAsignado;
    }

    public void setDoctorAsignado(Doctor doctorAsignado) {
        this.doctorAsignado = doctorAsignado;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public String getGenero() { return genero; }
    public void setGenero(String genero) {
        this.genero = genero;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public EtapaParkinson getEtapaParkinson() { return etapaParkinson; }
    public void setEtapaParkinson(EtapaParkinson etapaParkinson) {
        this.etapaParkinson = etapaParkinson;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public String getHistorialMedico() { return historialMedico; }
    public void setHistorialMedico(String historialMedico) {
        this.historialMedico = historialMedico;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public String getMedicamentosActuales() { return medicamentosActuales; }
    public void setMedicamentosActuales(String medicamentosActuales) {
        this.medicamentosActuales = medicamentosActuales;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public String getContactoEmergencia() { return contactoEmergencia; }
    public void setContactoEmergencia(String contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public String getTelefonoEmergencia() { return telefonoEmergencia; }
    public void setTelefonoEmergencia(String telefonoEmergencia) {
        this.telefonoEmergencia = telefonoEmergencia;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public String getEdad() { return edad; }
    public void setEdad(String edad) {
        this.edad = edad;
        this.fechaActualizacion = LocalDateTime.now();
    }

    // ✅ NUEVOS GETTERS Y SETTERS
    public LocalDate getFechaDiagnostico() { return fechaDiagnostico; }
    public void setFechaDiagnostico(LocalDate fechaDiagnostico) {
        this.fechaDiagnostico = fechaDiagnostico;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public String getComorbilidades() { return comorbilidades; }
    public void setComorbilidades(String comorbilidades) {
        this.comorbilidades = comorbilidades;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public String getAlergias() { return alergias; }
    public void setAlergias(String alergias) {
        this.alergias = alergias;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public String getAntecedentesFamiliares() { return antecedentesFamiliares; }
    public void setAntecedentesFamiliares(String antecedentesFamiliares) {
        this.antecedentesFamiliares = antecedentesFamiliares;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    // ✅ Método utilitario para calcular edad automáticamente
    public int calcularEdad() {
        if (this.fechaNacimiento == null) {
            return 0;
        }
        return java.time.Period.between(this.fechaNacimiento, LocalDate.now()).getYears();
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();

        // Calcular edad automáticamente si no está establecida
        if (this.edad == null && this.fechaNacimiento != null) {
            this.edad = String.valueOf(calcularEdad());
        }
    }
}