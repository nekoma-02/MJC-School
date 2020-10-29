package com.epam.esm.impl;

import com.epam.esm.GiftCertificateRepository;
import com.epam.esm.GiftCertificateService;
import com.epam.esm.ParamName;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String NOT_FOUND = "locale.message.CertificateNotFound";

    @Autowired
    private GiftCertificateRepository repo;

    @Override
    public boolean create(GiftCertificate certificate) {
        return repo.create(certificate);
    }

    @Override
    public boolean delete(long id) {
        Optional<GiftCertificate> giftCertificate = repo.findById(id);
        if (!giftCertificate.isPresent()) {
            throw new GiftCertificateNotFoundException(id, NOT_FOUND);
        }
        return repo.delete(id);
    }

    @Override
    public Optional<GiftCertificate> update(GiftCertificateDTO giftCertificate) {
        Optional<GiftCertificate> optionalGiftCertificate = repo.findById(giftCertificate.getId());
        if (!optionalGiftCertificate.isPresent()) {
            throw new GiftCertificateNotFoundException(giftCertificate.getId(), NOT_FOUND);
        }
        return repo.update(giftCertificate);
    }


    private static List<GiftCertificate> findByTag(String tagName, List<GiftCertificate> certificateList) {
        if (certificateList == null || tagName == null) {
            return Collections.emptyList();
        }
        return certificateList
                .stream()
                .filter(c -> c.getTagList()
                        .stream()
                        .anyMatch(t -> t.getName().equals(tagName)))
                .collect(Collectors.toList());
    }


    private static List<GiftCertificate> sortByDateAsc(List<GiftCertificate> certificateList) {
        if (certificateList == null) {
            return Collections.emptyList();
        }
        return certificateList
                .stream()
                .sorted(Comparator.comparing(GiftCertificate::getCreateDate))
                .collect(Collectors.toList());
    }


    private static List<GiftCertificate> sortByDateDesc(List<GiftCertificate> certificateList) {
        if (certificateList == null) {
            return Collections.emptyList();
        }
        return certificateList
                .stream()
                .sorted(Comparator.comparing(GiftCertificate::getCreateDate)
                        .reversed())
                .collect(Collectors.toList());
    }


    private static List<GiftCertificate> sortByNameAsc(List<GiftCertificate> certificateList) {
        if (certificateList == null) {
            return Collections.emptyList();
        }
        return certificateList
                .stream()
                .sorted(Comparator.comparing(GiftCertificate::getName))
                .collect(Collectors.toList());
    }


    private static List<GiftCertificate> sortByNameDesc(List<GiftCertificate> certificateList) {
        if (certificateList == null) {
            return Collections.emptyList();
        }
        return certificateList
                .stream()
                .sorted(Comparator.comparing(GiftCertificate::getName)
                        .reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        Optional<GiftCertificate> giftCertificate = repo.findById(id);
        if (!giftCertificate.isPresent()) {
            throw new GiftCertificateNotFoundException(id, NOT_FOUND);
        }

        return repo.findById(id);
    }

    @Override
    @Transactional
    public void addTagToCertificate(List<Tag> tagList, long id) {
        Optional<GiftCertificate> giftCertificate = repo.findById(id);
        if (!giftCertificate.isPresent()) {
            throw new GiftCertificateNotFoundException(id, NOT_FOUND);
        }
        repo.addTagToCertificate(tagList, id);
    }

    @Override
    public List<GiftCertificate> getAll() {
        return repo.getAll();
    }

    @Override
    public List<GiftCertificate> getFilteredListCertificates(String param, String sort, List<GiftCertificate> certificateList) {
        if (param != null) {
            certificateList = findByTag(param, certificateList);
        }

        if (sort != null) {
            if (ParamName.NAME_ASC.equals(ParamName.valueOf(sort.toUpperCase()))) {
                certificateList = sortByNameAsc(certificateList);
            }

            if (ParamName.NAME_DESC.equals(ParamName.valueOf(sort.toUpperCase()))) {
                certificateList = sortByNameDesc(certificateList);
            }

            if (ParamName.DATE_ASC.equals(ParamName.valueOf(sort.toUpperCase()))) {
                certificateList = sortByDateAsc(certificateList);
            }

            if (ParamName.DATE_DESC.equals(ParamName.valueOf(sort.toUpperCase()))) {
                certificateList = sortByDateDesc(certificateList);
            }
        }
        return certificateList;
    }

}
