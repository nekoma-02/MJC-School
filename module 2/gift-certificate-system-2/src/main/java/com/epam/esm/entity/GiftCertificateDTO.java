package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;


@Data
@JsonDeserialize(builder = com.epam.esm.entity.GiftCertificateDTO.GiftCertificateDTOBuilder.class)
@Builder(builderClassName = "GiftCertificateDTOBuilder")
public class GiftCertificateDTO {

    private long id;
    private String name;
    private String description;
    private double price;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp lastUpdateDate;
    private String timeZone_LastUpdateDate;
    private int duration;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GiftCertificateDTOBuilder {

    }


}
