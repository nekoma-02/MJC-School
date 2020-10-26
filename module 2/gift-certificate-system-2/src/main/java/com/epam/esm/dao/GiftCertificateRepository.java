package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDTO;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {
    Optional<GiftCertificate> create(GiftCertificate certificate);

    boolean delete(long id);

    Optional<GiftCertificate> update(GiftCertificateDTO giftCertificate);

    Optional<GiftCertificate> findById(long id);

    void addTagToCertificate(List<Tag> tagList, long id);

    List<GiftCertificate> getAll();
}
