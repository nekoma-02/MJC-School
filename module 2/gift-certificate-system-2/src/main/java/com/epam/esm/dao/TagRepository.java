package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    boolean create(Tag tag);

    boolean delete(String name);

    Optional<Tag> findById(long id);

    Optional<Tag> findByName(String name);

    List<Tag> getAll();
}
