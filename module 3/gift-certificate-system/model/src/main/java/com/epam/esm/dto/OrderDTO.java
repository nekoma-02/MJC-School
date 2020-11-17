package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.cert.Certificate;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private ZonedDateTime orderDate;
    private List<GiftCertificate> certificateList;
}
