package es.fplumara.dam1.prestamos.app;

import es.fplumara.dam1.prestamos.csv.CSVMaterialExporter;
import es.fplumara.dam1.prestamos.csv.CSVMaterialImporter;
import es.fplumara.dam1.prestamos.csv.RegistroMaterialCsv;
import es.fplumara.dam1.prestamos.exception.DuplicadoMaterialIdException;
import es.fplumara.dam1.prestamos.exception.MaterialNoDisponibleException;
import es.fplumara.dam1.prestamos.exception.NoEncontradoException;
import es.fplumara.dam1.prestamos.model.*;
import es.fplumara.dam1.prestamos.repository.Repository;
import es.fplumara.dam1.prestamos.repository.impl.MaterialRepositoryImpl;
import es.fplumara.dam1.prestamos.repository.impl.PrestamoRepositoryImpl;
import es.fplumara.dam1.prestamos.service.MaterialServiceImpl;
import es.fplumara.dam1.prestamos.service.PrestamoServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Main de ejemplo para demostrar el flujo mínimo del examen (sin menú complejo).
 * La idea es que este método ejecute una "demo" por consola.
 */
public class Main {

    public static void main(String[] args) throws DuplicadoMaterialIdException, NoEncontradoException, MaterialNoDisponibleException {
        System.out.println("Examen DAM1 - Préstamo de Material (Java 21)");

        /*
         * FLUJO MÍNIMO OBLIGATORIO (lo que debe hacer tu main)
         *
         * 1) Crear repositorios en memoria
         *
         *    - Crear MaterialRepositoryImpl (almacena materiales en memoria).
         *    - Crear PrestamoRepositoryImpl (almacena préstamos en memoria).
         */
        System.out.println("Examen DAM1 - Préstamo de Material (Java 21)");

        Repository<Material>  materialRepository = new MaterialRepositoryImpl();
        Repository<Prestamo>  prestamoRepository = new PrestamoRepositoryImpl();

        /*
         * 2) Crear servicios
         *    - Crear MaterialService usando el repositorio de materiales.
         *    - Crear PrestamoService usando el repositorio de materiales y el de préstamos.
         *
         */

        MaterialServiceImpl materialService = new MaterialServiceImpl(materialRepository);
        PrestamoServiceImpl prestamoService = new PrestamoServiceImpl(materialRepository,prestamoRepository);

        /*
         * 3) Cargar materiales desde CSV (código proporcionado)
         *    - Usar CsvMaterialImporter para leer "materiales.csv".
         *    - El importer devuelve registros (por ejemplo RegistroMaterialCsv).
         *    - Convertir cada registro a tu modelo:
         *        - Si tipo == "PORTATIL" -> crear Portatil (extra = ramGB)
         *        - Si tipo == "PROYECTOR" -> crear Proyector (extra = lumens)
         *      (aplicando estado y etiquetas)
         *    - Registrar cada Material llamando a MaterialService.registrarMaterial(...)
         *
         */
        CSVMaterialImporter csvMaterialImporter = new CSVMaterialImporter();
        List<RegistroMaterialCsv> registroMaterialCsvList = csvMaterialImporter.leer("C:\\Users\\USER\\Documents\\SAMUEL\\fpsumma\\Programacion\\prestamos-Samuel\\data\\materiales.csv");


        for (RegistroMaterialCsv registroMaterialCsv : registroMaterialCsvList) {
            if(registroMaterialCsv.tipo().equalsIgnoreCase("PORTATIL")){
                Portatil portatil = new Portatil();
                portatil.setId(registroMaterialCsv.id());
                portatil.setName(registroMaterialCsv.nombre());
                String estado= registroMaterialCsv.estado();
                if(estado.equalsIgnoreCase("DISPONIBLE")){
                    portatil.setEstado(EstadoMaterial.DISPONIBLE);
                }else if (estado.equalsIgnoreCase("BAJA")){
                    portatil.setEstado(EstadoMaterial.BAJA);
                }else{
                    portatil.setEstado(EstadoMaterial.PRESTADO);
                }
                portatil.setEtiquetas(registroMaterialCsv.etiquetas());
                portatil.setRamGB(Integer.parseInt(String.valueOf(registroMaterialCsv.extra())));
                materialService.registrarMaterial(portatil);

            } else{
                Proyector proyector = new Proyector();
                proyector.setId(registroMaterialCsv.id());
                proyector.setName(registroMaterialCsv.nombre());
                String estado= registroMaterialCsv.estado();
                if(estado.equalsIgnoreCase("DISPONIBLE")){
                    proyector.setEstado(EstadoMaterial.DISPONIBLE);
                }else if (estado.equalsIgnoreCase("BAJA")){
                    proyector.setEstado(EstadoMaterial.BAJA);
                }else{
                    proyector.setEstado(EstadoMaterial.PRESTADO);
                }
                proyector.setEtiquetas(registroMaterialCsv.etiquetas());
                proyector.setLumens(Integer.parseInt(String.valueOf(registroMaterialCsv.extra())));
                materialService.registrarMaterial(proyector);
            }
        }



        /* 4) Crear un préstamo
         *    - Elegir un id de material existente (por ejemplo "M001").
         *    - Llamar a PrestamoService.crearPrestamo("M001", "Nombre Profesor", fecha)
         *    - Comprobar que el material pasa a estado PRESTADO
         */
        String materialId = "Q004";
        prestamoService.crearPrestamo(materialId,"Raul", LocalDate.now());
        Material material = materialService.obtenerMaterial(materialId);
        System.out.println(material.getEstado());
        /* 5) Listar por consola
         *    - Imprimir todos los materiales (MaterialService.listar()) mostrando: id, nombre, estado, tipo.
         *    - Imprimir todos los préstamos (PrestamoService.listarPrestamos()) mostrando: id, idMaterial, profesor, fecha.
         */

        materialService.listarMateriales().forEach(System.out::println);
        prestamoService.listarPrestamos().forEach(p->System.out.println(p));


        /* 6) Devolver el material
         *    - Llamar a PrestamoService.devolverMaterial("M001")
         *    - Comprobar que vuelve a estado DISPONIBLE
         */
        prestamoService.devolverMaterial(materialId);
        System.out.println(material.getEstado());

        /* 7) Exportar a CSV (código proporcionado)
         *    - Convertir tu lista de Material a la estructura que pida el exporter (por ejemplo RegistroMaterialCsv).
         *    - Usar CsvMaterialExporter para escribir "salida_materiales.csv".
         */

        List<Material> materialList = materialService.listarMateriales();
        List<RegistroMaterialCsv> registroMaterialCsvListSalida = new ArrayList<>();
        for(Material m : materialList){
            RegistroMaterialCsv registroMaterialCsvSalida;
            if(m.getTipo().equalsIgnoreCase("PORTATIL")){
                Portatil p = (Portatil) m;
                registroMaterialCsvSalida = new RegistroMaterialCsv(m.getTipo(), m.getId(), m.getName(), m.getEstado().toString(),p.getRamGB(), m.getEtiquetas());

            }else{
                Proyector proyector = (Proyector) m;
                registroMaterialCsvSalida = new RegistroMaterialCsv(m.getTipo(), m.getId(), m.getName(), m.getEstado().toString(),proyector.getLumens(), m.getEtiquetas());
            }
            registroMaterialCsvListSalida.add(registroMaterialCsvSalida);

        }

        CSVMaterialExporter exporter = new CSVMaterialExporter();
        exporter.escribir("C:\\Users\\USER\\Documents\\SAMUEL\\fpsumma\\Programacion\\prestamos-Samuel\\data\\materiales.csv",registroMaterialCsvListSalida);

        /* Nota:
         * - No hace falta interfaz, ni menú, ni pedir datos por teclado: valores fijos y salida por consola es suficiente.
         */
    }
}