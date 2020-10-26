package com.epam.esm.controller;

import com.epam.esm.entity.Error;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.TagNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/all")
    public List<Tag> getAllTags() {

        return tagService.getAll();
    }


    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable long id) {
        Tag tag = tagService.findById(id);
        return tag;
    }

    @PostMapping
    public boolean createTag(@RequestBody Tag tag) {
        return tagService.create(tag);
    }

    @DeleteMapping("/{name}")
    public boolean deleteTag(@PathVariable String name) {
        return tagService.delete(name);
    }

    @ExceptionHandler(TagNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error tagNotFound(TagNotFoundException e) {
        return new Error(40401,e.getMessage() + " id = " + e.getId());
    }

}
