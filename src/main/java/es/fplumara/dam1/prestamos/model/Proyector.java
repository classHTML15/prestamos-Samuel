package es.fplumara.dam1.prestamos.model;

import java.util.Set;

public class Proyector extends Material {

    private Integer lumens;

    public Integer getLumens() {
        return lumens;
    }

    public void setLumens(Integer lumens) {
        this.lumens = lumens;
    }

    public Proyector(String id, String name, EstadoMaterial estado, Set<String> etiquetas, Integer lumens) {
        super(id, name, estado, etiquetas, lumens);
    }
}
