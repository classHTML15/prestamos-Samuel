package es.fplumara.dam1.prestamos.model;

import java.util.Set;

public class Proyector extends Material {

    private Integer lumens;

    public Proyector(String id, String name, EstadoMaterial estado, Set<String> etiquetas) {
        super(id, name, estado, etiquetas);
    }
}
