package com.epam.esm.controller;

import com.epam.esm.TagService;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@RequestMapping("/tags")
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL_FORMS)
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping
    public List<Tag> getAllTags(@Valid Pagination pagination) {
        List<Tag> tagList = tagService.getAll(pagination);
        tagList.stream().forEach(tag -> tag.add(linkTo(methodOn(TagController.class).getTagById(tag.getId())).withSelfRel()));
        return tagService.getAll(pagination);
    }

    @GetMapping("/{id}")
    public EntityModel<Tag> getTagById(@PathVariable long id) {
        Tag tag = tagService.findById(id);
        return EntityModel.of(tag, linkTo(methodOn(TagController.class)
                .getTagById(id)).withSelfRel()
                .andAffordance(afford(methodOn(TagController.class).deleteTag(id))));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Tag> createTag(@RequestBody @Valid Tag tag) {
        Tag createdTag = tagService.create(tag);
        return EntityModel.of(createdTag, linkTo(methodOn(TagController.class)
                .createTag(tag)).withSelfRel()
                .andAffordance(afford(methodOn(TagController.class).deleteTag(createdTag.getId())))
                .andAffordance(afford(methodOn(TagController.class).getTagById(createdTag.getId()))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable long id) {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
