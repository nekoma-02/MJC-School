package com.epam.esm.entity;

import com.epam.esm.util.ZoneIdConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Data
@JsonDeserialize(builder = Order.OrderBuilder.class)
@Builder(builderClassName = "OrderBuilder")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orders")
public class Order extends RepresentationModel<Order> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "OrderDate")
    private LocalDateTime orderDate;
    @Column(name = "TimeZone_OrderDate")
    @Convert(converter = ZoneIdConverter.class)
    private ZoneId orderDateTimeZone;
    @Column(name = "Cost")
    private BigDecimal cost;

    @ManyToMany
    @JoinTable(name = "Order_has_certificate",
            joinColumns = @JoinColumn(name = "Order_id"),
            inverseJoinColumns = @JoinColumn(name = "GiftCertificate_id"))
    private List<GiftCertificate> CertificateList;

    @ManyToOne
    @JoinColumn(name="User_id", nullable=false, insertable = false, updatable = false)
    private User user;

}
