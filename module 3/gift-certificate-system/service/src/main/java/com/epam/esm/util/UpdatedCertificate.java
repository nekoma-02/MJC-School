package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UpdatedCertificate {
    private final static List<String> wrongMapKeys = Arrays.asList("links");
    private final static List<Object> wrongMapValues = Arrays.asList(null, 0L);

    @Autowired
    private ObjectMapper objectMapper;

    public GiftCertificate getUpdatedGiftCertificate(GiftCertificate oldCertificate, GiftCertificate newCertificate) {
        Map<String, Object> newCertificateMap = giftCertificateToMap(newCertificate);
        Map<String, Object> oldCertificateMap = giftCertificateToMap(oldCertificate);
        oldCertificateMap.putAll(newCertificateMap);
        return objectMapper.convertValue(oldCertificateMap, GiftCertificate.class);
    }

    private Map<String, Object> giftCertificateToMap(GiftCertificate certificate) {
        Map<String, Object> certificateMap = objectMapper.convertValue(certificate, HashMap.class);
        certificateMap.values().removeAll(wrongMapValues);
        certificateMap.keySet().removeAll(wrongMapKeys);
        return certificateMap;
    }
}
