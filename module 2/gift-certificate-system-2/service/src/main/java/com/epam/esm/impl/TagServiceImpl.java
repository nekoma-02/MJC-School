package com.epam.esm.impl;

import com.epam.esm.TagRepository;
import com.epam.esm.TagService;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private static final String NOT_FOUND = "locale.message.TagNotFound";
    private static final String ENTITY_EXIST = "locale.message.TagExist";

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag create(Tag tag) {
        if (tagRepository.findByName(tag.getName()).isPresent()) {
            throw new EntityExistException(ENTITY_EXIST, tag.getName());
        }
        return tagRepository.create(tag).get();
    }

    @Override
    public boolean delete(String name) {
        tagRepository.findByName(name);
        return tagRepository.delete(name);
    }

    @Override
    public Tag findById(long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (!tag.isPresent()) {
            throw new EntityNotFoundException(NOT_FOUND,id);
        }
        return tag.get();
    }

    @Override
    public Tag findByName(String name) {
        Optional<Tag> tag = tagRepository.findByName(name);
        if (!tag.isPresent()) {
            throw new EntityNotFoundException(NOT_FOUND);
        }
        return tag.get();
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.getAll();
    }


}
