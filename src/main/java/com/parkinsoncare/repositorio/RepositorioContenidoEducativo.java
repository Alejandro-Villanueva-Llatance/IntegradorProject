package com.parkinsoncare.repositorio;

import com.parkinsoncare.modelo.ContenidoEducativo;
import com.parkinsoncare.modelo.enumeraciones.TipoContenido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioContenidoEducativo extends JpaRepository<ContenidoEducativo, Long> {
    List<ContenidoEducativo> findByTipoContenido(TipoContenido tipoContenido);
    List<ContenidoEducativo> findByActivoTrue();
    List<ContenidoEducativo> findByTipoContenidoAndActivoTrue(TipoContenido tipoContenido);
}