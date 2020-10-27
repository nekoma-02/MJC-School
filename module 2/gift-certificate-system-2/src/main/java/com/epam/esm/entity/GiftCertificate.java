package com.epam.esm.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@JsonDeserialize(builder = GiftCertificate.GiftCertificateBuilder.class)
@Builder(builderClassName = "GiftCertificateBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificate implements Serializable {
    private static final long serialVersionUID = 1234325L;

    private long id;
    private String name;
    private String description;
    private double price;
    private ZonedDateTime createDate;
    private ZonedDateTime lastUpdateDate;
    private int duration;
    private List<Tag> tagList;


    @JsonPOJOBuilder(withPrefix = "")
    public static class GiftCertificateBuilder {

    }

}
