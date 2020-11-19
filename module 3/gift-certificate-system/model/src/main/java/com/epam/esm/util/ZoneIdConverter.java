package com.epam.esm.util;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import java.time.ZoneId;

public class ZoneIdConverter implements AttributeConverter<ZoneId, String> {
    @Override
    public String convertToDatabaseColumn(ZoneId zoneId) {
        if(zoneId != null) {
            return zoneId.getId();
        }
        return StringUtils.EMPTY;
    }

    @Override
    public ZoneId convertToEntityAttribute(String s) {
        return ZoneId.of(s);
    }
}
