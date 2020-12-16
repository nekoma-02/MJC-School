package com.epam.esm.audit.entity;

import com.epam.esm.audit.AuditAction;
import com.epam.esm.util.ZoneIdConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gift_certificate_aud")
public class GiftCertificateHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private long entityId;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private BigDecimal price;
    @Column(name = "CreateDate")
    private LocalDateTime createDate;
    @Column(name = "TimeZone_CreateDate")
    @Convert(converter = ZoneIdConverter.class)
    private ZoneId createDateTimeZone;
    @Column(name = "LastUpdateDate")
    private LocalDateTime lastUpdateDate;
    @Column(name = "TimeZone_LastUpdateDate")
    @Convert(converter = ZoneIdConverter.class)
    private ZoneId lastUpdateDateTimeZone;
    @Column
    private Integer duration;
    @Enumerated(EnumType.STRING)
    private AuditAction auditAction;
}
