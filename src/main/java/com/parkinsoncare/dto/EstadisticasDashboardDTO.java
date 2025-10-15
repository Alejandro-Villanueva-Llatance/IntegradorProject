package com.parkinsoncare.dto;

import java.util.Map;

public class EstadisticasDashboardDTO {
    private int citasHoy;
    private int recomendacionesPendientes;
    private int sintomasRegistrados;
    private Map<String, Double> promediosSintomas;

    // Constructores
    public EstadisticasDashboardDTO() {}

    // Getters y setters
    public int getCitasHoy() { return citasHoy; }
    public void setCitasHoy(int citasHoy) { this.citasHoy = citasHoy; }

    public int getRecomendacionesPendientes() { return recomendacionesPendientes; }
    public void setRecomendacionesPendientes(int recomendacionesPendientes) { this.recomendacionesPendientes = recomendacionesPendientes; }

    public int getSintomasRegistrados() { return sintomasRegistrados; }
    public void setSintomasRegistrados(int sintomasRegistrados) { this.sintomasRegistrados = sintomasRegistrados; }

    public Map<String, Double> getPromediosSintomas() { return promediosSintomas; }
    public void setPromediosSintomas(Map<String, Double> promediosSintomas) { this.promediosSintomas = promediosSintomas; }
}