package com.parkinsoncare.repositorio;

import com.parkinsoncare.modelo.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioDoctor extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUsername(String username);
    Optional<Doctor> findByEmail(String email);
    boolean existsByNumeroLicencia(String numeroLicencia);
}