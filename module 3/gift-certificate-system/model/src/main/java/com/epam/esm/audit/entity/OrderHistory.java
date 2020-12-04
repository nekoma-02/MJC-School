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
@Table(name = "order_aud")
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private long entityId;
    @Column(name = "OrderDate")
    private LocalDateTime orderDate;
    @Column(name = "TimeZone_OrderDate")
    @Convert(converter = ZoneIdConverter.class)
    private ZoneId orderDateTimeZone;
    @Column(name = "Cost")
    private BigDecimal cost;
    @Column(name = "User_id")
    private long userId;
    @Enumerated(EnumType.STRING)
    private AuditAction auditAction;
}
