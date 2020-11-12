package com.epam.esm.controller;

import com.epam.esm.TagService;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tag")
public class TagController {
    private static final String DELETE_LINK = "delete";
    @Autowired
    private TagService tagService;

    @GetMapping
    public List<Tag> getAllTags(Pagination pagination) {
        List<Tag> tagList = tagService.getAll(pagination);
        tagList.stream().forEach(tag -> tag.add(linkTo(methodOn(TagController.class).getTagById(tag.getId())).withSelfRel()));
        return tagService.getAll(pagination);
    }

    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable long id) {
        Tag tag = tagService.findById(id);
        tag.add(linkTo(methodOn(TagController.class).getTagById(id)).withSelfRel());
        tag.add(linkTo(methodOn(TagController.class).deleteTag(tag.getName())).withRel(DELETE_LINK));
        return tag;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        Tag tag1 =tagService.create(tag);
        tag.add(linkTo(methodOn(TagController.class).createTag(tag1)).withSelfRel());
        ResponseEntity<Tag> responseEntity = new ResponseEntity<>(tag1, HttpStatus.CREATED);
        return responseEntity;
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteTag(@PathVariable String name) {
         tagService.delete(name);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
