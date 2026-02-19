package es.fplumara.dam1.prestamos.service;

import es.fplumara.dam1.prestamos.exception.DuplicadoMaterialIdException;
import es.fplumara.dam1.prestamos.exception.MaterialNoDisponibleException;
import es.fplumara.dam1.prestamos.exception.NoEncontradoException;
import es.fplumara.dam1.prestamos.model.EstadoMaterial;
import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.repository.Repository;

import java.util.List;

public class MaterialServiceImpl {
    private Repository<Material> repositoryMaterial;

    public MaterialServiceImpl(Repository<Material> repositoryMaterial) {
        this.repositoryMaterial = repositoryMaterial;
    }

    public void registrarMaterial(Material material) {
        if (material.getId() == null || material == null || material.getId().isBlank()) {
            throw new IllegalArgumentException("Material o id son invalidos");
        }

        if(repositoryMaterial.findById(material.getId()).isPresent()) {
            throw new DuplicadoMaterialIdException("Material duplicado");
        }
        repositoryMaterial.save(material);
    }

    public void darDeBaja(String materialId) {
        if(materialId != null) {
            throw new NoEncontradoException("El id del material es invalido");
        }

        Material material = repositoryMaterial.findById(materialId).get();
        if (material == null) {
            throw new NoEncontradoException("Material no encontrado");
        }

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