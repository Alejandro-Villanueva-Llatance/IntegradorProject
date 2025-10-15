#!/bin/bash

echo "🔄 Completando estructura del proyecto Parkinson Care..."

BASE_DIR="src/main/java/com/parkinsoncare"
RESOURCES_DIR="src/main/resources"

# Crear estructura completa de directorios
mkdir -p $BASE_DIR/configuracion
mkdir -p $BASE_DIR/controlador
mkdir -p $BASE_DIR/servicio
mkdir -p $BASE_DIR/repositorio
mkdir -p $BASE_DIR/modelo
mkdir -p $BASE_DIR/modelo/enumeraciones
mkdir -p $BASE_DIR/dto
mkdir -p $BASE_DIR/dto/autenticacion
mkdir -p $BASE_DIR/utilidades
mkdir -p $BASE_DIR/excepcion

mkdir -p $RESOURCES_DIR/contenido-educativo/ejercicios
mkdir -p $RESOURCES_DIR/contenido-educativo/nutricion
mkdir -p $RESOURCES_DIR/contenido-educativo/consejos-diarios

# Renombrar archivo de aplicación principal
mv src/main/java/com/parkinsoncare/SistemaCuidadoParkinsonApplication.java \
   src/main/java/com/parkinsoncare/AplicacionParkinsonCare.java

# Actualizar contenido del archivo de aplicación
cat > src/main/java/com/parkinsoncare/AplicacionParkinsonCare.java << 'APPEOF'
package com.parkinsoncare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AplicacionParkinsonCare {

    public static void main(String[] args) {
        SpringApplication.run(AplicacionParkinsonCare.class, args);
    }
}
APPEOF

# Crear archivos de configuración
cat > $BASE_DIR/configuracion/ConfiguracionSeguridad.java << 'CONFEOF'
package com.parkinsoncare.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConfiguracionSeguridad {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/doctor/**").hasAnyRole("DOCTOR", "ADMIN")
                .requestMatchers("/api/paciente/**").hasAnyRole("PACIENTE", "DOCTOR", "ADMIN")
                .requestMatchers("/api/public/**").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
CONFEOF

cat > $BASE_DIR/configuracion/ConfiguracionOpenAI.java << 'AIEOF'
package com.parkinsoncare.configuracion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracionOpenAI {
    
    @Value("\${openai.api.key}")
    private String apiKey;
    
    @Value("\${openai.api.url}")
    private String apiUrl;
    
    // Getters
    public String getApiKey() { return apiKey; }
    public String getApiUrl() { return apiUrl; }
}
AIEOF

# Crear DTOs de autenticación
cat > $BASE_DIR/dto/autenticacion/SolicitudLogin.java << 'DTOEOF'
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
DTOEOF

cat > $BASE_DIR/dto/autenticacion/RespuestaLogin.java << 'RESEOF'
package com.parkinsoncare.dto.autenticacion;

public class RespuestaLogin {
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
    
    // Getters y Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public String getTipoToken() { return tipoToken; }
    public void setTipoToken(String tipoToken) { this.tipoToken = tipoToken; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
RESEOF

# Crear controlador de autenticación
cat > $BASE_DIR/controlador/ControladorAutenticacion.java << 'CTRLEOF'
package com.parkinsoncare.controlador;

import org.springframework.web.bind.annotation.*;
import com.parkinsoncare.dto.autenticacion.SolicitudLogin;
import com.parkinsoncare.dto.autenticacion.RespuestaLogin;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class ControladorAutenticacion {
    
    @PostMapping("/login")
    public RespuestaLogin login(@RequestBody SolicitudLogin solicitud) {
        // TODO: Implementar lógica de autenticación real
        System.out.println("Login attempt: " + solicitud.getUsername());
        
        // Simulación temporal
        return new RespuestaLogin("token-temporal", solicitud.getUsername(), "PACIENTE");
    }
    
    @PostMapping("/registro")
    public String registro(@RequestBody SolicitudLogin solicitud) {
        // TODO: Implementar registro de usuarios
        return "Registro en desarrollo para: " + solicitud.getUsername();
    }
}
CTRLEOF

# Crear modelo de Usuario
cat > $BASE_DIR/modelo/Usuario.java << 'MODELEOF'
package com.parkinsoncare.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String nombre;
    
    private String telefono;
    
    @Enumerated(EnumType.STRING)
    private RolUsuario rol;
    
    private LocalDateTime fechaCreacion;
    
    private boolean activo = true;
    
    // Constructores
    public Usuario() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public RolUsuario getRol() { return rol; }
    public void setRol(RolUsuario rol) { this.rol = rol; }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
MODELEOF

# Crear enumeración de roles
cat > $BASE_DIR/modelo/enumeraciones/RolUsuario.java << 'ENUMEOF'
package com.parkinsoncare.modelo.enumeraciones;

public enum RolUsuario {
    PACIENTE,
    DOCTOR, 
    ADMIN
}
ENUMEOF

# Crear DTO de respuesta API
cat > $BASE_DIR/dto/RespuestaAPI.java << 'APIRESEOF'
package com.parkinsoncare.dto;

public class RespuestaAPI {
    private boolean success;
    private String message;
    private Object data;
    
    // Constructores
    public RespuestaAPI(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    
    public RespuestaAPI(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    
    // Métodos estáticos para respuestas rápidas
    public static RespuestaAPI success(String message, Object data) {
        return new RespuestaAPI(true, message, data);
    }
    
    public static RespuestaAPI error(String message) {
        return new RespuestaAPI(false, message);
    }
    
    // Getters y Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
}
APIRESEOF

# Crear archivo application.properties adicional
cat > $RESOURCES_DIR/application-dev.properties << 'PROPEOF'
# Configuración Desarrollo
spring.datasource.url=jdbc:postgresql://localhost:5432/parkinson_care
spring.datasource.username=parkinson_user
spring.datasource.password=parkinson_pass

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# OpenAI Configuration
openai.api.key=your-openai-key-here
openai.api.url=https://api.openai.com/v1/chat/completions

# JWT Secret (cambiar en producción)
jwt.secret=parkinsonCareSecretKey2024

# Server Configuration
server.port=8080
PROPEOF

echo "✅ Estructura completada exitosamente!"
echo "📁 Proyecto ubicado en: $(pwd)"
echo "🎯 Para abrir en IntelliJ: idea ."
echo "🚀 Para ejecutar: ./mvnw spring-boot:run"
