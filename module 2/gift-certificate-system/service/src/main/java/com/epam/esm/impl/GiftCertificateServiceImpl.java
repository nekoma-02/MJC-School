package com.epam.esm.impl;

import com.epam.esm.GiftCertificateRepository;
import com.epam.esm.GiftCertificateService;
import com.epam.esm.entity.ParamName;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String NOT_FOUND = "locale.message.CertificateNotFound";
    private static final String CERTIFICATE_EXIST = "locale.message.CertificateExist";

    @Autowired
    private GiftCertificateRepository repo;

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        if (repo.findByName(certificate.getName()).isPresent()) {
            throw new EntityExistException(CERTIFICATE_EXIST, certificate.getName());
        }
        return repo.create(certificate).get();
    }

    @Override
    public boolean delete(long id) {
        Optional<GiftCertificate> giftCertificate = repo.findById(id);
        if (!giftCertificate.isPresent()) {
            throw new EntityNotFoundException(NOT_FOUND, id);
        }
        return repo.delete(id);
    }

    @Override
    public GiftCertificate update(GiftCertificateDTO giftCertificate, long id) {
        Optional<GiftCertificate> optionalGiftCertificate = repo.findById(id);
        if (!optionalGiftCertificate.isPresent()) {
            throw new EntityNotFoundException(NOT_FOUND, id);
        }
        if (repo.findByName(giftCertificate.getName()).isPresent()) {
            throw new EntityExistException(CERTIFICATE_EXIST, giftCertificate.getName());
        }
        giftCertificate.setId(id);
        return repo.update(giftCertificate).get();
    }

    @Override
    public GiftCertificate findById(long id) {
        Optional<GiftCertificate> giftCertificate = repo.findById(id);
        if (!giftCertificate.isPresent()) {
            throw new EntityNotFoundException(NOT_FOUND, id);
        }
        return giftCertificate.get();
    }

    @Override
    @Transactional
    public GiftCertificate addTagToCertificate(List<Tag> tagList, long id) {
        Optional<GiftCertificate> giftCertificate = repo.findById(id);
        if (!giftCertificate.isPresent()) {
            throw new EntityNotFoundException(NOT_FOUND, id);
        }
        List<Tag> actualTags = giftCertificate.get().getTagList();
        actualTags.addAll(tagList);
        actualTags.stream().distinct().collect(Collectors.toList());
        return repo.addTagToCertificate(actualTags, id).get();
    }

    @Override
    public List<GiftCertificate> getAll() {
        return repo.getAll();
    }

    @Override
    public List<GiftCertificate> getFilteredListCertificates(Map<String, String> filterParam) {
        deleteIllegalSearchParam(filterParam);
        deleteIllegalSortParam(filterParam);
        return repo.filterCertificate(filterParam);
    }

    private void deleteIllegalSearchParam(Map<String, String> filterParam) {
        filterParam.entrySet().removeIf(
                entry -> Arrays.stream(ParamName.values()).noneMatch(e -> e.getParamName().equals(entry.getKey()))
        );
    }

    private void deleteIllegalSortParam(Map<String, String> filterParam) {
        filterParam.entrySet().removeIf(
                entry -> ParamName.getPossibleDirectionParam().stream().noneMatch(e -> {
                    if (entry.getKey().equals(ParamName.DIRECTION.getParamName())) {
                        return entry.getValue().equals(e);
                    }
                    return true;
                })
        );
        filterParam.entrySet().removeIf(
                entry -> ParamName.getPossibleFieldParam().stream().noneMatch(e -> {
                    if (entry.getKey().equals(ParamName.FIELD.getParamName())) {
                        return entry.getValue().equals(e);
                    }
                    return true;
                })
        );
    }

}
