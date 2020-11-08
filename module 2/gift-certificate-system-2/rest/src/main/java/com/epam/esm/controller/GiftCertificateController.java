package com.epam.esm.controller;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.TagService;
import com.epam.esm.entity.Error;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDTO;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/certificate")
public class GiftCertificateController {

    @Autowired
    private GiftCertificateService certificateService;

    @Autowired
    private TagService tagService;

    @GetMapping
    public List<GiftCertificate> getGiftCertificate(@RequestParam Map<String,String> filterParam) {
        if (filterParam.isEmpty()) {
            return certificateService.getAll();
        }
        return certificateService.getFilteredListCertificates(filterParam);
    }

    @GetMapping("/{id}")
    public GiftCertificate getGiftCertificate(@PathVariable long id) {
        return certificateService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGiftCertificate(@PathVariable long id) {
        certificateService.delete(id);
    }

    @PostMapping
    public ResponseEntity<GiftCertificate> createCertificate(@RequestBody GiftCertificate giftCertificate) {
        GiftCertificate giftCertificate1 = certificateService.create(giftCertificate);
        ResponseEntity<GiftCertificate> responseEntity = new ResponseEntity<>(giftCertificate1, HttpStatus.CREATED);
        return responseEntity;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificate> updateCertificate(@PathVariable long id, @RequestBody GiftCertificateDTO giftCertificate) {
        GiftCertificate giftCertificate1 =  certificateService.update(giftCertificate,id);
        ResponseEntity<GiftCertificate> responseEntity = new ResponseEntity<>(giftCertificate1, HttpStatus.CREATED);
        return responseEntity;
    }

    @PostMapping("/binding/{id}")
    public ResponseEntity<GiftCertificate> addTagToCertificate(@RequestBody List<Tag> tagList, @PathVariable long id) {
        tagList.stream().distinct().forEach(t -> tagService.findByName(t.getName()));
        GiftCertificate giftCertificate = certificateService.addTagToCertificate(tagList, id);
        ResponseEntity<GiftCertificate> responseEntity = new ResponseEntity<>(giftCertificate, HttpStatus.CREATED);
        return responseEntity;
    }

}
