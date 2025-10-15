package com.parkinsoncare.dto;

import com.parkinsoncare.modelo.enumeraciones.PrioridadRecomendacion;

public class RecomendacionDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private PrioridadRecomendacion prioridad;
    private String categoria;
    private boolean completada;


    public RecomendacionDTO() {}


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
}