package com.epam.esm.controller;

import com.epam.esm.entity.Error;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDTO;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.GiftCertificateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/certificate")
public class GiftCertificateController {

    @Autowired
    private GiftCertificateService service;

    @GetMapping
    public List<GiftCertificate> getAllCertificate() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GiftCertificate getGiftCertificate(@PathVariable long id) {
        Optional<GiftCertificate> giftCertificate = service.findById(id);
        return giftCertificate.get();
    }

    @DeleteMapping("/{id}")
    public void deleteGiftCertificate(@PathVariable long id) {
        service.delete(id);
    }

    @PostMapping
    public void createCertificate(@RequestBody GiftCertificate giftCertificate) {
        Optional<GiftCertificate> optionalGiftCertificate = service.create(giftCertificate);

    }

    @PatchMapping("/{id}")
    public void updateCertificate(@PathVariable long id, @RequestBody GiftCertificateDTO giftCertificate) {
        Optional<GiftCertificate> optionalGiftCertificate = service.findById(id);

        if (!optionalGiftCertificate.isPresent()) {
            throw new GiftCertificateNotFoundException(id,"certificate not found");
        }

        giftCertificate.setId(id);
        service.update(giftCertificate);

    }


    @ExceptionHandler(GiftCertificateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error CertificateNotFound(GiftCertificateNotFoundException e) {
        return new Error(40401,e.getMessage() + " id = " + e.getId());
    }


}
