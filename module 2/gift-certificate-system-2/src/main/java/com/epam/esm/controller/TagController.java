package com.epam.esm.controller;

import com.epam.esm.entity.Error;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.TagNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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
    @ResponseStatus(HttpStatus.CREATED)
    public void createTag(@RequestBody Tag tag) {
         tagService.create(tag);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(@PathVariable String name) {
         tagService.delete(name);
    }

    @ExceptionHandler(TagNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error tagNotFound(TagNotFoundException e) {
        Locale locale = new Locale("en");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("locale/locale",locale);
        return new Error(40401, resourceBundle.getString(e.getMessage()) + " id = " + e.getId());
    }

}
