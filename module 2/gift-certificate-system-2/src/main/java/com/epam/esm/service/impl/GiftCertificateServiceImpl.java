package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.GiftCertificateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    @Autowired
    private GiftCertificateRepository repo;

    @Override
    public Optional<GiftCertificate> create(GiftCertificate certificate) {
        return repo.create(certificate);
    }

    @Override
    public boolean delete(long id) {

        Optional<GiftCertificate> giftCertificate = repo.findById(id);
        if (!giftCertificate.isPresent()) {
            throw new GiftCertificateNotFoundException(id,"Gift Certificate not found");
        }

        return repo.delete(id);
    }

    @Override
    public Optional<GiftCertificate> update(GiftCertificateDTO giftCertificate) {
        return repo.update(giftCertificate);
    }

    @Override
    public List<GiftCertificate> findByTag(String tagName, List<GiftCertificate> certificateList) {
        return null;
    }

    @Override
    public List<GiftCertificate> sortByDateAsc(List<GiftCertificate> certificateList) {
        return null;
    }

    @Override
    public List<GiftCertificate> sortByDateDesc(List<GiftCertificate> certificateList) {
        return null;
    }

    @Override
    public List<GiftCertificate> sortByNameAsc(List<GiftCertificate> certificateList) {
        return null;
    }

    @Override
    public List<GiftCertificate> sortByNameDesc(List<GiftCertificate> certificateList) {
        return null;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {

        Optional<GiftCertificate> giftCertificate = repo.findById(id);
        if (!giftCertificate.isPresent()) {
            throw new GiftCertificateNotFoundException(id,"Gift Certificate not found");
        }


        return repo.findById(id);
    }

    @Override
    public void addTagToCertificate(List<Tag> tagList, long id) {
        repo.addTagToCertificate(tagList,id);
    }

    @Override
    public List<GiftCertificate> getAll() {
        return repo.getAll();
    }
}
