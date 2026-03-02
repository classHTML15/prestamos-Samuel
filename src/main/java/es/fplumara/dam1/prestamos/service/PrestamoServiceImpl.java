package es.fplumara.dam1.prestamos.service;

import es.fplumara.dam1.prestamos.exception.MaterialNoDisponibleException;
import es.fplumara.dam1.prestamos.exception.NoEncontradoException;
import es.fplumara.dam1.prestamos.model.EstadoMaterial;
import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.model.Prestamo;
import es.fplumara.dam1.prestamos.repository.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PrestamoServiceImpl {

    private Repository<Material> materialRepository;
    private Repository<Prestamo> prestamoRepository;

    public PrestamoServiceImpl(Repository<Material> materialRepository, Repository<Prestamo> prestamoRepository) {
        this.materialRepository = materialRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public Prestamo crearPrestamo(String materialId, String profesor, LocalDate fecha) throws NoEncontradoException, MaterialNoDisponibleException {
        //Si algún parámetro es null/vacío (o fecha null)
        if(materialId == null || materialId.isEmpty() || profesor == null || profesor.isEmpty() || fecha == null) {
            throw new IllegalArgumentException("Los parametros son invalidos");
        }

        //Si no existe material con ese id → NoEncontradoException
        Optional<Material> material = materialRepository.findById(materialId);
        if(material.isEmpty()) {
            throw new NoEncontradoException("El identificador del material no existe" + materialId);
        }
        Material m = material.get();

        //Si existe, pero su estado no es DISPONIBLE → MaterialNoDisponibleException
        if (m.getEstado() != EstadoMaterial.DISPONIBLE) {
            throw new MaterialNoDisponibleException("Material no esta disponible");
        }

        m.setEstado(EstadoMaterial.PRESTADO);
        materialRepository.save(m);


        String prestamoId = UUID.randomUUID().toString();
        Prestamo nuevoPrestamo = new Prestamo(prestamoId, materialId, profesor, fecha);
        prestamoRepository.save(nuevoPrestamo);

        return nuevoPrestamo;

    }

    public void devolverMaterial(String materialId) {
        //Si idMaterial es null/vacío → IllegalArgumentException
        if(materialId == null || materialId.isEmpty()) {
            throw new IllegalArgumentException("El identificador del material esta vacio");
        }
        //Si no existe material → NoEncontradoException
        Optional<Material> materialExistente = materialRepository.findById(materialId);
        if(materialExistente.isEmpty()) {
            throw new NoEncontradoException("Material no esta disponible");
        }
        Material m = materialExistente.get();

        //Si existe pero su estado no es PRESTADO → MaterialNoDisponibleException
        if(m.getEstado() != (EstadoMaterial.PRESTADO)) {
            throw new MaterialNoDisponibleException("Material existente no esta prestado");
        }
        m.setEstado(EstadoMaterial.DISPONIBLE);
        materialRepository.save(m);

    }

    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.listAll();
    }

}
