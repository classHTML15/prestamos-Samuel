package es.fplumara.dam1.prestamos.model;

import java.util.Set;

public class Proyector extends Material {

    private int lumens;

    public Proyector() {
    }

    public Proyector(String id, String name, EstadoMaterial estado, Set<String> etiquetas, Integer lumens) {
        super(id, name, estado);
        this.lumens = lumens;
    }

    public int getLumens() {
        return lumens;
    }

    public void setLumens(int lumens) {
        this.lumens = lumens;
    }

    @Override
    public String getTipo() {
        return "PROYECTOR";
    }
}
