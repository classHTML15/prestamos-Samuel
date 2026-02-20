package es.fplumara.dam1.prestamos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import es.fplumara.dam1.prestamos.model.EstadoMaterial;
import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.model.Prestamo;
import es.fplumara.dam1.prestamos.repository.Repository;
import es.fplumara.dam1.prestamos.repository.impl.MaterialRepositoryImpl;
import es.fplumara.dam1.prestamos.repository.impl.PrestamoRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

class PrestamosServiceTest {

    @Mock
    MaterialRepositoryImpl materialRepository;

    @Mock
    PrestamoRepositoryImpl prestamoRepository;

    @InjectMocks
    PrestamoServiceImpl prestamoService;

    @Test
        // - crearPrestamo_ok_cambiaEstado_y_guarda()
    void crearPrestamo_ok_cambiaEstado_y_guarda() {
        Material material = mock(Material.class);
        when(materialRepository.findById(material.getId())).thenReturn(Optional.of(material));
        when(material.getId()).thenReturn(material.getId());
        when(material.getEstado()).thenReturn(EstadoMaterial.DISPONIBLE);

        Prestamo prestamo = prestamoService.crearPrestamo(material.getId(), "Samuel", LocalDate.now());
    }
    // TODO (alumnos): añadir JUnit 5 y Mockito en el pom.xml y completar:

    // - crearPrestamo_materialNoExiste_lanzaNoEncontrado()
    
    // - crearPrestamo_materialNoDisponible_lanzaMaterialNoDisponible()
    // - devolverMaterial_ok_cambiaADisponible()
    //
    // Requisito: usar mocks de repositorios y verify(...)
}
