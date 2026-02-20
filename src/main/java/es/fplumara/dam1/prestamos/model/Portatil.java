package es.fplumara.dam1.prestamos.model;

import java.util.Set;

public class Portatil extends Material {

    private int ramGB;


    public Portatil(String id, String name, EstadoMaterial estado, int ramGB) {
        super(id, name, estado, ramGB);
    }

    public int getRamGB() {
        return ramGB;
    }

    public void setRamGB(int ramGB) {
        this.ramGB = ramGB;
    }

}
