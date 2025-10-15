package com.parkinsoncare.servicio;

import com.parkinsoncare.modelo.ContenidoEducativo;
import com.parkinsoncare.modelo.enumeraciones.TipoContenido;
import com.parkinsoncare.repositorio.RepositorioContenidoEducativo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioContenidoEducativo {

    @Autowired
    private RepositorioContenidoEducativo repositorioContenidoEducativo;

    public List<ContenidoEducativo> obtenerTodoContenidoActivo() {
        return repositorioContenidoEducativo.findByActivoTrue();
    }

    public List<ContenidoEducativo> obtenerContenidoPorTipo(TipoContenido tipoContenido) {
        return repositorioContenidoEducativo.findByTipoContenidoAndActivoTrue(tipoContenido);
    }

    public List<ContenidoEducativo> obtenerEjercicios() {
        return repositorioContenidoEducativo.findByTipoContenidoAndActivoTrue(TipoContenido.EJERCICIO);
    }

    public List<ContenidoEducativo> obtenerConsejosNutricion() {
        return repositorioContenidoEducativo.findByTipoContenidoAndActivoTrue(TipoContenido.NUTRICION);
    }

    public List<ContenidoEducativo> obtenerConsejosDiarios() {
        return repositorioContenidoEducativo.findByTipoContenidoAndActivoTrue(TipoContenido.CONSEJO_DIARIO);
    }

    public ContenidoEducativo obtenerContenidoPorId(Long id) {
        return repositorioContenidoEducativo.findById(id)
                .orElseThrow(() -> new RuntimeException("Contenido educativo no encontrado"));
    }
}