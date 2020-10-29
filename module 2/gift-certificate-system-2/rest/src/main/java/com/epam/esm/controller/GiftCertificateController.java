package com.epam.esm.controller;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.entity.Error;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateNotFoundException;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/certificate")
public class GiftCertificateController {

    @Autowired
    private GiftCertificateService service;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public List<GiftCertificate> getAllCertificate() {
        return service.getAll();
    }

    @GetMapping("/list")
    public List<GiftCertificate> getGiftCertificate(@RequestParam(required = false) String param, @RequestParam(required = false) String sort) {
        List<GiftCertificate> certificateList = service.getAll();
        service.getFilteredListCertificates(param, sort, certificateList);
        return certificateList;
    }

    @GetMapping("/{id}")
    public GiftCertificate getGiftCertificate(@PathVariable long id) {
        Optional<GiftCertificate> giftCertificate = service.findById(id);
        return giftCertificate.get();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGiftCertificate(@PathVariable long id) {
        service.delete(id);
    }

    @PostMapping
    public ResponseEntity<Boolean> createCertificate(@RequestBody GiftCertificate giftCertificate) {
        boolean isCreated = service.create(giftCertificate);
        ResponseEntity<Boolean> responseEntity = new ResponseEntity<>(isCreated, HttpStatus.CREATED);
        return responseEntity;
    }

    @PatchMapping("/{id}")
    public void updateCertificate(@PathVariable long id, @RequestBody GiftCertificateDTO giftCertificate) {
        Optional<GiftCertificate> optionalGiftCertificate = service.findById(id);

        if (!optionalGiftCertificate.isPresent()) {
            throw new GiftCertificateNotFoundException(id, "certificate not found");
        }
        giftCertificate.setId(id);
        service.update(giftCertificate);
    }

    @PostMapping("/binding/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTagToCertificate(@RequestBody List<Tag> tagList, @PathVariable long id) {
        service.addTagToCertificate(tagList, id);
    }

    @ExceptionHandler(GiftCertificateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error CertificateNotFound(GiftCertificateNotFoundException e, Locale locale) {
        return new Error(40402, messageSource.getMessage(e.getMessage(), null, locale) + " id = " + e.getId());
    }
}
