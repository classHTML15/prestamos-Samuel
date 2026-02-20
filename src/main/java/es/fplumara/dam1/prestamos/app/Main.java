package es.fplumara.dam1.prestamos.app;

import es.fplumara.dam1.prestamos.csv.CSVMaterialImporter;
import es.fplumara.dam1.prestamos.csv.RegistroMaterialCsv;
import es.fplumara.dam1.prestamos.model.EstadoMaterial;
import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.model.Portatil;
import es.fplumara.dam1.prestamos.model.Prestamo;
import es.fplumara.dam1.prestamos.repository.Repository;
import es.fplumara.dam1.prestamos.repository.impl.MaterialRepositoryImpl;
import es.fplumara.dam1.prestamos.repository.impl.PrestamoRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Main de ejemplo para demostrar el flujo mínimo del examen (sin menú complejo).
 * La idea es que este método ejecute una "demo" por consola.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Examen DAM1 - Préstamo de Material (Java 21)");

        /*
         * FLUJO MÍNIMO OBLIGATORIO (lo que debe hacer tu main)
         *
         * 1) Crear repositorios en memoria
         *    - Crear MaterialRepositoryImpl (almacena materiales en memoria).
         *    - Crear PrestamoRepositoryImpl (almacena préstamos en memoria).
        */
        Repository<Material> materialRepository = new MaterialRepositoryImpl();
        Repository<Prestamo> prestamoRepository = new PrestamoRepositoryImpl();
        /*
         * 2) Crear servicios
         *    - Crear MaterialService usando el repositorio de materiales.
         *    - Crear PrestamoService usando el repositorio de materiales y el de préstamos.
        */
        MaterialRepositoryImpl materialRepositoryImpl = new MaterialRepositoryImpl();
        PrestamoRepositoryImpl prestamoRepositoryImpl = new PrestamoRepositoryImpl();
        /*
         * 3) Cargar materiales desde CSV (código proporcionado)
         *    - Usar CsvMaterialImporter para leer "materiales.csv".
         *    - El importer devuelve registros (por ejemplo RegistroMaterialCsv).
         *    - Convertir cada registro a tu modelo:
         *        - Si tipo == "PORTATIL" -> crear Portatil (extra = ramGB)
         *        - Si tipo == "PROYECTOR" -> crear Proyector (extra = lumens)
         *      (aplicando estado y etiquetas)
         *    - Registrar cada Material llamando a MaterialService.registrarMaterial(...)
         */
        CSVMaterialImporter csvMaterialImporter = new CSVMaterialImporter();
        List<RegistroMaterialCsv> registroMaterialCsvList = csvMaterialImporter.leer("C:\\Users\\SamuelJosuéQuinteroA\\OneDrive - SUMMA Formación Profesional\\Documents\\IntelliJ\\prestamos-Samuel\\data\\materiales.csv");
        List<Material> materials = new ArrayList<Material>();
        for (RegistroMaterialCsv registroMaterialCsv : registroMaterialCsvList) {
            if(registroMaterialCsv.tipo().equalsIgnoreCase("PORTATIL")){
                Material portatil1 = new Portatil();
                portatil1.setId(registroMaterialCsv.id());
                portatil1.setName(registroMaterialCsv.nombre());
                String state = registroMaterialCsv.estado();
                if(state.equalsIgnoreCase("DISPONIBLE")) {
                    portatil1.setEstado(EstadoMaterial.DISPONIBLE);
                } else if (state.equalsIgnoreCase("BAJA")) {
                    portatil1.setEstado(EstadoMaterial.BAJA);
                } else{
                    portatil1.setEstado(EstadoMaterial.PRESTADO);
                }
                portatil1.setEtiquetas(registroMaterialCsv.etiquetas());
            }
        }
        /*
         * 4) Crear un préstamo
         *    - Elegir un id de material existente (por ejemplo "M001").
         *    - Llamar a PrestamoService.crearPrestamo("M001", "Nombre Profesor", fecha)
         *    - Comprobar que el material pasa a estado PRESTADO
         */

         /* 5) Listar por consola
         *    - Imprimir todos los materiales (MaterialService.listar()) mostrando: id, nombre, estado, tipo.
         *    - Imprimir todos los préstamos (PrestamoService.listarPrestamos()) mostrando: id, idMaterial, profesor, fecha.
         */

         /* 6) Devolver el material
         *    - Llamar a PrestamoService.devolverMaterial("M001")
         *    - Comprobar que vuelve a estado DISPONIBLE
         */

         /* 7) Exportar a CSV (código proporcionado)
         *    - Convertir tu lista de Material a la estructura que pida el exporter (por ejemplo RegistroMaterialCsv).
         *    - Usar CsvMaterialExporter para escribir "salida_materiales.csv".
         */

            /*
         * Nota:
         * - No hace falta interfaz, ni menú, ni pedir datos por teclado: valores fijos y salida por consola es suficiente.
         */
    }
}