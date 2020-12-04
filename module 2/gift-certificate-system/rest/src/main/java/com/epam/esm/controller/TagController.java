package com.epam.esm.controller;

import com.epam.esm.TagService;
import com.epam.esm.entity.Error;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.List;
import java.util.Locale;


@RestController
@RequestMapping("/tag")

public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAll();
    }

    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable long id) {
        Tag tag = tagService.findById(id);
        return tag;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
         Tag tag1 =tagService.create(tag);
        ResponseEntity<Tag> responseEntity = new ResponseEntity<>(tag1, HttpStatus.CREATED);
        return responseEntity;
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(@PathVariable String name) {
         tagService.delete(name);
    }

}
