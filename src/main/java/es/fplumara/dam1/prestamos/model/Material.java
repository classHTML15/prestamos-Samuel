package es.fplumara.dam1.prestamos.model;

import java.util.Set;

public abstract class Material {

    private String id;
    private String name;
    private EstadoMaterial estado;
    private Set<String> etiquetas;

    public Material(String id, String name, EstadoMaterial estado, Set<String> etiquetas) {
        this.id = id;
        this.name = name;
        this.estado = estado;
        this.etiquetas = etiquetas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EstadoMaterial getEstado() {
        return estado;
    }

    public void setEstado(EstadoMaterial estado) {
        this.estado = estado;
    }

    public Set<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(Set<String> etiquetas) {
        this.etiquetas = etiquetas;
    }
}
