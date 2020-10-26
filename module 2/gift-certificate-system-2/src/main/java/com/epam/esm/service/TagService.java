package com.epam.esm.service;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagService {
    boolean create(Tag tag);
    boolean delete(String name);
    Tag findById(long id);
    Tag findByName(String name);
    List<Tag> getAll();
}
