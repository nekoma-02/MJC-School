package com.epam.esm.controller;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.TagService;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/certificates")
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL_FORMS)
public class GiftCertificateController {

    @Autowired
    private GiftCertificateService certificateService;

    @Autowired
    private TagService tagService;

    @GetMapping
    public List<GiftCertificate> getGiftCertificate(@RequestParam Map<String, String> filterParam, Pagination pagination) {
        List<GiftCertificate> certificateList;
        if (!filterParam.isEmpty() || filterParam != null) {
            certificateList = certificateService.getFilteredListCertificates(filterParam);
        } else {
            certificateList = certificateService.getAll(pagination);
        }
        certificateList
                .stream()
                .forEach(certificate -> certificate.add(linkTo(methodOn(GiftCertificateController.class)
                        .getGiftCertificate(certificate.getId())).withSelfRel()));
        return certificateList;
    }

    @GetMapping("/{id}")
    public EntityModel<GiftCertificate> getGiftCertificate(@PathVariable long id) {
        GiftCertificate giftCertificate = certificateService.findById(id);
        return EntityModel.of(giftCertificate, linkTo(methodOn(GiftCertificateController.class)
                .getGiftCertificate(id)).withSelfRel()
                .andAffordance(afford(methodOn(GiftCertificateController.class).updateCertificate(id, giftCertificate)))
                .andAffordance(afford(methodOn(GiftCertificateController.class).deleteGiftCertificate(id))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGiftCertificate(@PathVariable long id) {
        certificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<GiftCertificate> createCertificate(@RequestBody GiftCertificate giftCertificate) {
        GiftCertificate createdCertificate = certificateService.create(giftCertificate);
        return EntityModel.of(createdCertificate, linkTo(methodOn(GiftCertificateController.class)
                .createCertificate(giftCertificate)).withSelfRel()
                .andAffordance(afford(methodOn(GiftCertificateController.class).updateCertificate(createdCertificate.getId(), giftCertificate)))
                .andAffordance(afford(methodOn(GiftCertificateController.class).getGiftCertificate(createdCertificate.getId())))
                .andAffordance(afford(methodOn(GiftCertificateController.class).deleteGiftCertificate(createdCertificate.getId()))));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<GiftCertificate> updateCertificate(@PathVariable long id, @RequestBody GiftCertificate giftCertificate) {
        GiftCertificate updatedCertificate = certificateService.update(giftCertificate, id);
        return EntityModel.of(updatedCertificate, linkTo(methodOn(GiftCertificateController.class)
                .updateCertificate(id, giftCertificate)).withSelfRel()
                .andAffordance(afford(methodOn(GiftCertificateController.class).getGiftCertificate(updatedCertificate.getId())))
                .andAffordance(afford(methodOn(GiftCertificateController.class).deleteGiftCertificate(updatedCertificate.getId()))));
    }

    @PostMapping("/binding/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<GiftCertificate> addTagToCertificate(@RequestBody List<Tag> tagList, @PathVariable long id) {
        tagList.stream().distinct().forEach(tag -> {
            tagService.findByName(tag.getName());
            tag.add(linkTo(methodOn(TagController.class).getTagById(tag.getId())).withSelfRel());
        });
        GiftCertificate giftCertificate = certificateService.addTagToCertificate(tagList, id);
        return EntityModel.of(giftCertificate, linkTo(methodOn(GiftCertificateController.class)
                .addTagToCertificate(tagList, id)).withSelfRel()
                .andAffordance(afford(methodOn(GiftCertificateController.class).getGiftCertificate(giftCertificate.getId())))
                .andAffordance(afford(methodOn(GiftCertificateController.class).deleteGiftCertificate(giftCertificate.getId())))
                .andAffordance(afford(methodOn(GiftCertificateController.class).updateCertificate(id, giftCertificate))));
    }

}
