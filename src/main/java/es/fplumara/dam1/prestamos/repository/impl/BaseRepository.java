package es.fplumara.dam1.prestamos.repository.impl;

import es.fplumara.dam1.prestamos.model.Identificable;
import es.fplumara.dam1.prestamos.repository.Repository;

import java.util.List;
import java.util.Optional;

public class BaseRepository implements Repository {

    @Override
    public void save(Identificable Identificable) {

    }

    @Override
    public Optional findById(String id) {
        return Optional.empty();
    }

    @Override
    public List listAll() {
        return List.of();
    }

    @Override
    public void delete(String id) {

    }
}
