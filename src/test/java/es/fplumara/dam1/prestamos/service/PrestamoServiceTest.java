package es.fplumara.dam1.prestamos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import es.fplumara.dam1.prestamos.exception.MaterialNoDisponibleException;
import es.fplumara.dam1.prestamos.exception.NoEncontradoException;
import es.fplumara.dam1.prestamos.model.EstadoMaterial;
import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.model.Portatil;
import es.fplumara.dam1.prestamos.model.Prestamo;
import es.fplumara.dam1.prestamos.repository.Repository;
import es.fplumara.dam1.prestamos.repository.impl.MaterialRepositoryImpl;
import es.fplumara.dam1.prestamos.repository.impl.PrestamoRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PrestamosServiceTest {
    /*
    @Mock
    private MaterialRepositoryImpl materialRepository;

    @Mock
    private Repository<Prestamo> prestamoRepository;

    @InjectMocks
    private PrestamoServiceImpl prestamoService;
    String materialId = "Q004";
    String profesor = "Raul";
    LocalDate fecha = LocalDate.now();
    */
    private MaterialRepositoryImpl materialRepository;
    private Repository<Prestamo> prestamoRepository;
    private PrestamoServiceImpl prestamoService;

    private String materialId = "Q004";
    private String profesor = "Raul";
    private LocalDate fecha = LocalDate.now();

    @BeforeEach
    void setUp() {
        // 1. Crea los mocks MANUALMENTE
        materialRepository = mock(MaterialRepositoryImpl.class);
        prestamoRepository = mock(Repository.class);

        // 2. Crea el servicio con los mocks (inyección manual)
        prestamoService = new PrestamoServiceImpl(materialRepository, prestamoRepository);
    }

    @Test
        // - crearPrestamo_ok_cambiaEstado_y_guarda()
    void crearPrestamo_ok_cambiaEstado_y_guarda() throws NoEncontradoException {

        Material material = new Portatil(materialId, "Portatil Aula 1", EstadoMaterial.DISPONIBLE, 20);
        when(materialRepository.findById(materialId)).thenReturn(Optional.of(material));

        Prestamo prestamo = prestamoService.crearPrestamo(materialId, "Raul", fecha);
        assertNotNull(prestamo);
        assertNotNull(prestamo.getId());
        assertEquals("M001", prestamo.getIdMaterial());
        assertEquals("Raul", prestamo.getProfesor());
        assertEquals(fecha, prestamo.getFecha());
        assertEquals(EstadoMaterial.PRESTADO, material.getEstado());

        verify(materialRepository, times(1)).findById(materialId);
        verify(prestamoRepository, times(1)).save(any(Prestamo.class));
        verify(materialRepository, times(1)).save(any(Material.class));
    }
    // TODO (alumnos): añadir JUnit 5 y Mockito en el pom.xml y completar:

    // - crearPrestamo_materialNoExiste_lanzaNoEncontrado()
    @Test
    void crearPrestamo_materialNoExiste_lanzaNoEcontrado()  throws NoEncontradoException {

        when(materialRepository.findById(materialId)).thenReturn(Optional.empty());
        assertThrows(NoEncontradoException.class, () -> {
            prestamoService.crearPrestamo(materialId, profesor, fecha);
        });

        verify(materialRepository, times(1)).findById(materialId);
        verify(prestamoRepository, never()).save(any());
        verify(materialRepository, never()).save(any());
    }

    // - crearPrestamo_materialNoDisponible_lanzaMaterialNoDisponible()
    @Test
    void crearPrestamo_materialNoDisponible_lanzaMaterialNoDisponible() throws MaterialNoDisponibleException {
        Material material = new Portatil(materialId, "Portátil Aula 2", EstadoMaterial.PRESTADO, 20);
        when(materialRepository.findById(materialId)).thenReturn(Optional.of(material));
        assertThrows(MaterialNoDisponibleException.class, () -> {
            prestamoService.crearPrestamo(materialId, profesor, fecha);
        });
        verify(materialRepository, times(1)).findById(materialId);
        verify(prestamoRepository, never()).save(any());
        verify(materialRepository, never()).save(any());
    }
    // - devolverMaterial_ok_cambiaADisponible()
    @Test
    void devolverMaterial_ok_cambiaADisponible() throws NoEncontradoException, MaterialNoDisponibleException {
        Material material = new Portatil(materialId, "Proyector Epson", EstadoMaterial.PRESTADO, 20);
        when(materialRepository.findById(materialId)).thenReturn(Optional.of(material));
        prestamoService.devolverMaterial(materialId);
        assertEquals(EstadoMaterial.DISPONIBLE, material.getEstado());
        verify(materialRepository, times(1)).findById(materialId);
        verify(materialRepository).save(material);
    }
    // Requisito: usar mocks de repositorios y verify(...)
}
