package com.epam.esm.entity;

import com.epam.esm.audit.listener.OrderListener;
import com.epam.esm.audit.listener.UserListener;
import com.epam.esm.util.ZoneIdConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(OrderListener.class)
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
    @Column(name = "User_id")
    private long userId;

    @ManyToMany
    @JoinTable(name = "Order_has_certificate",
            joinColumns = @JoinColumn(name = "Order_id"),
            inverseJoinColumns = @JoinColumn(name = "GiftCertificate_id"))
    @NotNull
    private List<GiftCertificate> CertificateList;

    @ManyToOne
    @JoinColumn(name="User_id", nullable=false, insertable = false, updatable = false)
    @Transient
    private User user;

}
