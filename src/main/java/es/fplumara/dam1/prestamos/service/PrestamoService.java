package es.fplumara.dam1.prestamos.service;

import es.fplumara.dam1.prestamos.exception.NoEncontradoException;
import es.fplumara.dam1.prestamos.model.EstadoMaterial;
import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.model.Prestamo;
import es.fplumara.dam1.prestamos.repository.Repository;

import java.time.LocalDate;
import java.util.UUID;

public class PrestamoService {

    private Repository<Material> materialRepository;
    private Repository<Prestamo> prestamoRepository;

    public PrestamoService(Repository<Material> materialRepository, Repository<Prestamo> prestamoRepository) {
        this.materialRepository = materialRepository;
        this.prestamoRepository = prestamoRepository;
    }

    Prestamo crarPrestamo(String materialId, String profesor, LocalDate fecha) {
        if(materialId == null || materialId.isEmpty() || profesor == null || profesor.isEmpty() || fecha == null) {
            throw new NoEncontradoException("Los parametros son ivalidos");
        }
        if(materialId != null && materialId.isEmpty()) {
            throw new NoEncontradoException("El identificador del material no existe");
        }

        Material material = materialRepository.findById(materialId).get();
        if (material == null) {
            throw new NoEncontradoException("El material no fue encontrado");
        }

        if (material.getEstado() != EstadoMaterial.DISPONIBLE) {
            throw new NoEncontradoException("Material no esta disponible por prestamo");
        }

        material.setEstado(EstadoMaterial.PRESTADO);
        materialRepository.save(material);

    }
}
