package es.fplumara.dam1.prestamos.service;

import es.fplumara.dam1.prestamos.exception.DuplicadoMaterialIdException;
import es.fplumara.dam1.prestamos.exception.MaterialNoDisponibleException;
import es.fplumara.dam1.prestamos.exception.NoEncontradoException;
import es.fplumara.dam1.prestamos.model.EstadoMaterial;
import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.repository.Repository;

import java.util.List;
import java.util.Optional;

public class MaterialServiceImpl {
    private Repository<Material> repositoryMaterial;

    public MaterialServiceImpl(Repository<Material> repositoryMaterial) {
        this.repositoryMaterial = repositoryMaterial;
    }

    public void registrarMaterial(Material material) {
        if(material == null) {
            throw new IllegalArgumentException("El material no puede estar vacio");
        }

        if(material.getId() == null || material.getId().isBlank()) {
            throw new IllegalArgumentException("El material no puede estar vacio");
        }

        Optional<Material> materialExistente = repositoryMaterial.findById(material.getId());
        if (materialExistente.isPresent()) {
            throw new DuplicadoMaterialIdException("El material ya existe en el sistema");
        }
        repositoryMaterial.save(material);
    }

    public Material obtenerMaterial(String materialId) {
        Optional<Material> materialExistente = repositoryMaterial.findById(materialId);
        return materialExistente.orElse(null);
    }


    public void darDeBaja(String materialId) {
        if(materialId == null || materialId.isBlank()) {
            throw new NoEncontradoException("El id del material es invalido");
        }

        Optional<Material> materialExistente = repositoryMaterial.findById(materialId);
        if (materialExistente.isEmpty()) {
            throw new NoEncontradoException("El material no existe");
        }

        Material material = materialExistente.get();
        if(material.getEstado() == EstadoMaterial.BAJA) {
            throw new MaterialNoDisponibleException("Material no disponible");
        }
        material.setEstado(EstadoMaterial.BAJA);
        repositoryMaterial.save(material);
    }

    public List<Material> listarMateriales(){

        return repositoryMaterial.listAll();
    }
}