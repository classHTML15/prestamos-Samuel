package es.fplumara.dam1.prestamos.model;

public interface Identificable {

    default String getId() {
        return null;
    }
}
