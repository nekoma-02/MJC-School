package com.epam.esm.service.impl;

import com.epam.esm.dao.TagRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.TagNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public boolean create(Tag tag) {
        return tagRepository.create(tag);
    }

    @Override
    public boolean delete(String name) {

        Optional<Tag> tag = tagRepository.findByName(name);

        if (!tag.isPresent()) {
            throw new TagNotFoundException(777, "tag not found");
        }

        return tagRepository.delete(name);
    }

    @Override
    public Tag findById(long id) {
        Optional<Tag> tag = tagRepository.findById(id);

        if (!tag.isPresent()) {
            throw new TagNotFoundException(id, "tag not found");
        }

        return tag.get();
    }

    @Override
    public Tag findByName(String name) {
        Optional<Tag> tag = tagRepository.findByName(name);

        if (!tag.isPresent()) {
            throw new TagNotFoundException(tag.get().getId(), "tag not found");
        }

        return tag.get();
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.getAll();
    }
}
