package com.epam.esm.controller;

import com.epam.esm.entity.Error;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.GiftCertificateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/certificate")
public class GiftCertificateController {


    @Autowired
    private GiftCertificateService service;

    @GetMapping
    public List<GiftCertificate> getAllCertificate() {
        return service.getAll();
    }

    @GetMapping("/list")
    public List<GiftCertificate> getGiftCertificate(@RequestParam(required = false) String param, @RequestParam(required = false) String sort) {
        List<GiftCertificate> certificateList = service.getAll();

        if (param != null) {
            certificateList = service.findByTag(param, certificateList);
        }

        if (sort != null) {

            if (ParamName.NAME_ASC.equals(ParamName.valueOf(sort.toUpperCase()))) {
                certificateList = service.sortByNameAsc(certificateList);
            }

            if (ParamName.NAME_DESC.equals(ParamName.valueOf(sort.toUpperCase()))) {
                certificateList = service.sortByNameDesc(certificateList);
            }

            if (ParamName.DATE_ASC.equals(ParamName.valueOf(sort.toUpperCase()))) {
                certificateList = service.sortByDateAsc(certificateList);
            }

            if (ParamName.DATE_DESC.equals(ParamName.valueOf(sort.toUpperCase()))) {
                certificateList = service.sortByDateDesc(certificateList);
            }
        }
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

    @PostMapping("/tagforcertificate/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTagToCertificate(@RequestBody List<Tag> tagList, @PathVariable long id) {
        service.addTagToCertificate(tagList, id);
    }


    @ExceptionHandler(GiftCertificateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error CertificateNotFound(GiftCertificateNotFoundException e) {

        Locale locale = new Locale("en");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("locale/locale", locale);
        return new Error(40402, resourceBundle.getString(e.getMessage()) + " id = " + e.getId());
    }


}
