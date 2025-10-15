package com.parkinsoncare.modelo;

import com.parkinsoncare.modelo.enumeraciones.TipoContenido;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contenido_educativo")
public class ContenidoEducativo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private String contenido;

    @Enumerated(EnumType.STRING)
    private TipoContenido tipoContenido;

    private String urlImagen;
    private String duracion; // Para videos o lecturas
    private boolean activo = true;
    private LocalDateTime fechaCreacion;

    // Constructores
    public ContenidoEducativo() {
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public TipoContenido getTipoContenido() { return tipoContenido; }
    public void setTipoContenido(TipoContenido tipoContenido) { this.tipoContenido = tipoContenido; }
    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }
    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}