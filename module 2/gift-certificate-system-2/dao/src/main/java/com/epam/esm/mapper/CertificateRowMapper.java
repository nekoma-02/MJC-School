package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CertificateRowMapper implements RowMapper<GiftCertificate> {

    private static final String ID = "gif_id";
    private static final String NAME = "gif_name";
    private static final String DESCRIPTION = "gift_description";
    private static final String PRICE = "Price";
    private static final String CREATE_DATE = "CreateDate";
    private static final String TIME_ZONE_CREATE_DATE = "TimeZone_CreateDate";
    private static final String LAST_UPDATE_DATE = "LastUpdateDate";
    private static final String TIME_ZONE_LAST_UPDATE_DATE = "TimeZone_LastUpdateDate";
    private static final String DURATION = "Duration";
    private static final String TAGS = "tags";
    private static final String SEPARATOR_CHARS = ", ";


    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
            LocalDateTime createDate = rs.getTimestamp(CREATE_DATE).toLocalDateTime();
            ZoneId timeZoneCreateDate = ZoneId.of(rs.getString(TIME_ZONE_CREATE_DATE));

            LocalDateTime lastUpdateDate = rs.getTimestamp(LAST_UPDATE_DATE).toLocalDateTime();
            ZoneId timeZoneLastUpdateDate = ZoneId.of(rs.getString(TIME_ZONE_LAST_UPDATE_DATE));

        return GiftCertificate.builder()
                .id(rs.getLong(ID))
                .name(rs.getString(NAME))
                .description(rs.getString(DESCRIPTION))
                .price(BigDecimal.valueOf(rs.getDouble(PRICE)))
                .createDate(ZonedDateTime.of(createDate,timeZoneCreateDate))
                .lastUpdateDate(ZonedDateTime.of(lastUpdateDate,timeZoneLastUpdateDate))
                .duration(rs.getInt(DURATION))
                .tagList(tagList(rs.getString(TAGS)))
                .build();
    }

    private List<Tag> tagList(String tag) {
        List<Tag> tagList = new ArrayList<>();
        if (StringUtils.isNotBlank(tag)) {
            String[] tempArray = StringUtils.splitPreserveAllTokens(tag, SEPARATOR_CHARS);
            for (int i = 0; i < tempArray.length; i+=2) {
                tagList.add(newTag(tempArray[i],tempArray[i+1]));
            }
            return tagList;
        }
        return Collections.emptyList();
    }

    private Tag newTag(String id, String name) {
        return StringUtils.isNumeric(id) ? new Tag(Integer.parseInt(id),name) : new Tag();
    }


}


