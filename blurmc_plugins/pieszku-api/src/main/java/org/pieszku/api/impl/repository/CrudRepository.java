package org.pieszku.api.impl.repository;

import org.pieszku.api.impl.Identifiable;
import org.pieszku.api.redis.packet.type.UpdateType;

import java.util.Collection;

public interface CrudRepository<ID, T extends Identifiable<ID>> extends Repository<ID, T> {


    void update(T object, ID field, UpdateType updateType);
    Collection<T> saveAll(Collection<T> objects);
    T find(ID id);
    Collection<T> findAll();
    boolean delete(T object);
}
