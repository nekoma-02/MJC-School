package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDTO;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {

    Optional<GiftCertificate> create(GiftCertificate certificate);

    boolean delete(long id);

    Optional<GiftCertificate> update(GiftCertificateDTO giftCertificate);

    List<GiftCertificate> findByTag(String tagName, List<GiftCertificate> certificateList);

    List<GiftCertificate> sortByDateAsc(List<GiftCertificate> certificateList);

    List<GiftCertificate> sortByDateDesc(List<GiftCertificate> certificateList);

    List<GiftCertificate> sortByNameAsc(List<GiftCertificate> certificateList);

    List<GiftCertificate> sortByNameDesc(List<GiftCertificate> certificateList);

    Optional<GiftCertificate> findById(long id);

    void addTagToCertificate(List<Tag> tagList, long id);

    List<GiftCertificate> getAll();
}
