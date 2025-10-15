package com.parkinsoncare.dto.autenticacion;

public class SolicitudLogin {
    private String username;
    private String password;
    
    // Constructores
    public SolicitudLogin() {}
    
    public SolicitudLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    // Getters y Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
