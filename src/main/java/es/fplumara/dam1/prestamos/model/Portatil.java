package es.fplumara.dam1.prestamos.model;

import java.util.Set;

public class Portatil extends Material {

    private int ramGB;

    public Portatil(String id, String name, EstadoMaterial estado, Set<String> etiquetas) {
        super(id, name, estado, etiquetas);
    }

}
