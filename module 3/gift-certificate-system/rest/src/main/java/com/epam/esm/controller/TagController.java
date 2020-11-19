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
@RequestMapping("/tags")
public class TagController {
    private static final String DELETE_LINK = "delete";
    public static final String FIND_LINK = "find";
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
        tag.add(linkTo(methodOn(TagController.class).deleteTag(tag.getId())).withRel(DELETE_LINK));
        return tag;
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        Tag createdTag = tagService.create(tag);
        tag.add(linkTo(methodOn(TagController.class).createTag(createdTag)).withSelfRel());
        tag.add(linkTo(methodOn(TagController.class).getTagById(createdTag.getId())).withRel(FIND_LINK));
        ResponseEntity<Tag> responseEntity = new ResponseEntity<>(createdTag, HttpStatus.CREATED);
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable long id) {
        String message = tagService.delete(id);
        return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
    }

}
