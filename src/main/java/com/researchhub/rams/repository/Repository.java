package com.researchhub.rams.repository;

import java.util.Optional;

public interface Repository<E, I> {
    E save(E entity);

    Optional<E> findById(I id);

    void deleteById(I id);
}
