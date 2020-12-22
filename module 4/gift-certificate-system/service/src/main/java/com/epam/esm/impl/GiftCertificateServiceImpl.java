package com.epam.esm.impl;

import com.epam.esm.GiftCertificateRepository;
import com.epam.esm.GiftCertificateService;
import com.epam.esm.TagService;
import com.epam.esm.util.FilterParamUtil;
import com.epam.esm.util.UpdatedCertificate;
import com.epam.esm.entity.Pagination;
import com.epam.esm.util.ParamName;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final String NOT_FOUND = "locale.message.CertificateNotFound";
    private static final String TAG_NOT_FOUND = "locale.message.TagNotFound";
    private static final String CERTIFICATE_EXIST = "locale.message.CertificateExist";
    @Autowired
    private GiftCertificateRepository certificateRepository;
    @Autowired
    private TagService tagService;
    @Autowired
    private UpdatedCertificate updatedCertificate;
    @Autowired
    private FilterParamUtil filterParamUtil;

    @Override
    @Transactional
    public GiftCertificate create(GiftCertificate certificate) {
        if (Objects.nonNull(certificateRepository.findByName(certificate.getName()))) {
            throw new EntityExistException(CERTIFICATE_EXIST, certificate.getName());
        }
        ZonedDateTime dateTime = ZonedDateTime.now();
        certificate.setCreateDate(dateTime.toLocalDateTime());
        certificate.setCreateDateTimeZone(dateTime.getZone());
        certificate.setLastUpdateDate(dateTime.toLocalDateTime());
        certificate.setLastUpdateDateTimeZone(dateTime.getZone());
        return certificateRepository.create(certificate);
    }

    @Override
    @Transactional
    public void delete(long id) {
        GiftCertificate giftCertificate = certificateRepository.findById(id);
        if (Objects.isNull(giftCertificate)) {
            throw new EntityNotFoundException(NOT_FOUND, id);
        }
        certificateRepository.delete(id);
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificate giftCertificate, long id) {
        GiftCertificate certificate = certificateRepository.findById(id);
        if (Objects.isNull(certificate)) {
            throw new EntityNotFoundException(NOT_FOUND, id);
        }
        if (Objects.nonNull(certificateRepository.findByName(giftCertificate.getName()))) {
            throw new EntityExistException(CERTIFICATE_EXIST, giftCertificate.getName());
        }
        GiftCertificate newCertificate = updatedCertificate.getUpdatedGiftCertificate(certificate, giftCertificate);
        return certificateRepository.update(newCertificate);
    }

    @Override
    public GiftCertificate findById(long id) {
        GiftCertificate giftCertificate = certificateRepository.findById(id);
        if (Objects.isNull(giftCertificate)) {
            throw new EntityNotFoundException(NOT_FOUND, id);
        }
        return giftCertificate;
    }

    @Override
    @Transactional
    public GiftCertificate addTagToCertificate(List<Tag> tagList, long id) {
        GiftCertificate giftCertificate = certificateRepository.findById(id);
        if (Objects.isNull(giftCertificate)) {
            throw new EntityNotFoundException(NOT_FOUND, id);
        }
        tagList.forEach(tag -> tagService.findById(tag.getId()));
        List<Tag> actualTags = new ArrayList<>(giftCertificate.getTagSet());
        actualTags.addAll(tagList);
        actualTags.stream().distinct().collect(Collectors.toList());
        certificateRepository.addTagToCertificate(actualTags, id);
        return certificateRepository.findById(id);
    }

    @Override
    public List<GiftCertificate> getAll(Pagination pagination) {
        return certificateRepository.getAll(pagination);
    }

    @Override
    public List<GiftCertificate> getFilteredListCertificates(Map<String, String> filterParam, Pagination pagination) {
        filterParamUtil.deleteWrongSearchParam(filterParam);
        filterParamUtil.deleteWrongSortParam(filterParam);
        return certificateRepository.filterCertificate(filterParam, pagination);
    }
}
