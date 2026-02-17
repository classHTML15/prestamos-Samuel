package es.fplumara.dam1.prestamos.repository.impl;

import es.fplumara.dam1.prestamos.model.Identificable;
import es.fplumara.dam1.prestamos.repository.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BaseRepository<T extends Identificable> implements Repository<T> {

    private Map<String, T> map = new HashMap<>();

    @Override
    public void save(T Identificable) {
        if(Identificable == null || Identificable.getId() == null) {
            throw new NullPointerException("El identificable es nulo");
        }
    }

    @Override
    public Optional findById(String id) {
        if(id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public List listAll() {
        return List.of(map.values().toArray());
    }

    @Override
    public void delete(String id) {
        if(id == null) {
            map.remove(id);
        }
    }
}
