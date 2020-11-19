package com.epam.esm.controller;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.TagService;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {

    @Autowired
    private GiftCertificateService certificateService;

    @Autowired
    private TagService tagService;

    @GetMapping
    public List<GiftCertificate> getGiftCertificate(@RequestParam Map<String, String> filterParam, Pagination pagination) {
        if (!filterParam.isEmpty() || filterParam != null) {
            return certificateService.getFilteredListCertificates(filterParam);
        }
        return certificateService.getAll(pagination);
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
    public ResponseEntity<GiftCertificate> updateCertificate(@PathVariable long id, @RequestBody GiftCertificate giftCertificate) {
        GiftCertificate giftCertificate1 = certificateService.update(giftCertificate, id);
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
