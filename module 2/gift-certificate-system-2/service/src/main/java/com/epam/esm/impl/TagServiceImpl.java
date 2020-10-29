package com.epam.esm.impl;

import com.epam.esm.TagRepository;
import com.epam.esm.TagService;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private static final String NOT_FOUND = "locale.message.TagNotFound";

    @Autowired
    private TagRepository tagRepository;

    @Override
    public boolean create(Tag tag) {
        if (tag == null) {
            return false;
        }
        return tagRepository.create(tag);
    }

    @Override
    public boolean delete(String name) {
        Optional<Tag> tag = tagRepository.findByName(name);

        if (!tag.isPresent()) {
            throw new TagNotFoundException(777, NOT_FOUND);
        }
        return tagRepository.delete(name);
    }

    @Override
    public Tag findById(long id) {
        Optional<Tag> tag = tagRepository.findById(id);

        if (!tag.isPresent()) {
            throw new TagNotFoundException(id, NOT_FOUND);
        }
        return tag.get();
    }

    @Override
    public Tag findByName(String name) {
        Optional<Tag> tag = tagRepository.findByName(name);

        if (!tag.isPresent()) {
            throw new TagNotFoundException(tag.get().getId(), NOT_FOUND);
        }
        return tag.get();
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.getAll();
    }
}
