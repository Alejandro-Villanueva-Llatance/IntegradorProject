package com.parkinsoncare.dto.autenticacion;

public class RespuestaLogin {
    private  String nombre;
    private String token;
    private String tipoToken = "Bearer";
    private String username;
    private String rol;
    
    // Constructores
    public RespuestaLogin() {}
    
    public RespuestaLogin(String token, String username, String rol) {
        this.token = token;
        this.username = username;
        this.rol = rol;
    }

    public RespuestaLogin(String token, String username, String rol, String nombre) {
        this.token = token;
        this.username = username;
        this.rol = rol;
        this.nombre = nombre;
    }

    // Getters y Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getTipoToken() { return tipoToken; }
    public void setTipoToken(String tipoToken) { this.tipoToken = tipoToken; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getNombre() { return nombre; }  // ← AGREGAR GETTER
    public void setNombre(String nombre) { this.nombre = nombre; }  // ← AGREGAR SETTER
}
