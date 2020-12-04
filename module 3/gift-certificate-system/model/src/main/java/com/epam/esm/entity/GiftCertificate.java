package com.epam.esm.entity;

import com.epam.esm.audit.listener.GiftCertificateListener;
import com.epam.esm.audit.listener.UserListener;
import com.epam.esm.util.ZoneIdConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@JsonDeserialize(builder = GiftCertificate.GiftCertificateBuilder.class)
@Builder(builderClassName = "GiftCertificateBuilder")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(GiftCertificateListener.class)
@Table(name = "GiftCertificate")
public class GiftCertificate extends RepresentationModel<GiftCertificate> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @NotBlank
    private String name;
    @Column
    @NotBlank
    private String description;
    @Column
    @PositiveOrZero
    @NotNull
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
    @NotNull
    @PositiveOrZero
    private Integer duration;
    @ManyToMany
    @JoinTable(name = "Tag_has_GiftCertificate",
    joinColumns = @JoinColumn(name = "GiftCertificate_id"),
    inverseJoinColumns = @JoinColumn(name = "Tag_id"))
    private Set<Tag> tagSet;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GiftCertificateBuilder {
    }

}
