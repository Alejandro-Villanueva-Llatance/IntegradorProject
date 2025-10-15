package com.parkinsoncare.repositorio;

import com.parkinsoncare.modelo.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioRol extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}