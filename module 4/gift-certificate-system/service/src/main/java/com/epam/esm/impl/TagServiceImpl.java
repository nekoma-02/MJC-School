package com.epam.esm.impl;

import com.epam.esm.TagRepository;
import com.epam.esm.TagService;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TagServiceImpl implements TagService {
    private static final String NOT_FOUND = "locale.message.TagNotFound";
    private static final String ENTITY_EXIST = "locale.message.TagExist";
    private static final String DELETED = "deleted";

    @Autowired
    private TagRepository tagRepository;

    @Override
    @Transactional
    public Tag create(Tag tag) {
        if (Objects.nonNull(tagRepository.findByName(tag.getName()))) {
            throw new EntityExistException(ENTITY_EXIST, tag.getName());
        }
        return tagRepository.create(tag);
    }

    @Override
    @Transactional
    public void delete(long id) {
        tagRepository.delete(id);
    }

    @Override
    public Tag findById(long id) {
        Tag tag = tagRepository.findById(id);
        if (Objects.isNull(tag)) {
            throw new EntityNotFoundException(NOT_FOUND,id);
        }
        return tag;
    }

    @Override
    public Tag findByName(String name) {
        Tag tag = tagRepository.findByName(name);
        if (Objects.isNull(tag)) {
            throw new EntityNotFoundException(NOT_FOUND);
        }
        return tag;
    }

    @Override
    public List<Tag> getAll(Pagination pagination) {
        return tagRepository.getAll(pagination);
    }
}
