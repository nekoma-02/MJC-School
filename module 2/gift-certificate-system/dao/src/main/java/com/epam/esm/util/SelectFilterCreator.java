package com.epam.esm.util;

import com.epam.esm.entity.ParamName;
import java.util.Map;
import java.util.Objects;

public class SelectFilterCreator {
    private static final String SELECT_FILTER = "select GiftCertificate.id as gif_id, " +
            "GiftCertificate.Name as gif_name, GiftCertificate.Description as gift_description,Price, CreateDate, " +
            "TimeZone_CreateDate,LastUpdateDate,TimeZone_LastUpdateDate, Duration, group_concat(concat(Tag_id , ' ' ,Tag.Name)) as tags " +
            "from Tag_has_GiftCertificate right join GiftCertificate on Tag_has_GiftCertificate.GiftCertificate_id = GiftCertificate.id " +
            "left join Tag on Tag_has_GiftCertificate.Tag_id = Tag.id group by gif_id ";
    private static final String HAVING = " having ";
    private static final String AND = " and ";
    private static final String END_OF_LIKE = "%' ";
    private static final String LIKE = " like '%";
    private static final String ORDER_BY = " order by ";
    private static final String SPACE = " ";

    public String createFilterQuery(Map<String, String> filterParam) {
        StringBuilder sb = new StringBuilder(SELECT_FILTER);
        String searchQuery = searchingParams(filterParam);
        String sortQuery = sortingParams(filterParam);
        if (!searchQuery.trim().isEmpty()) {
            sb.append(HAVING).append(searchQuery);
            sb.delete(sb.length() - AND.length(), sb.length());
        }
        sb.append(sortQuery);
        return sb.toString();
    }

    private String searchingParams(Map<String, String> filterParam) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : filterParam.entrySet()) {
            if (!entry.getKey().equals(ParamName.FIELD.getParamName())
                    && !entry.getKey().equals(ParamName.DIRECTION.getParamName())) {
                sb.append(entry.getKey());
                sb.append(LIKE);
                sb.append(entry.getValue());
                sb.append(END_OF_LIKE);
                sb.append(AND);
            }
        }
        return sb.toString();
    }

    private String sortingParams(Map<String, String> filterParam) {
        StringBuilder sb = new StringBuilder();
        String direction = filterParam.get(ParamName.DIRECTION.getParamName());
        String field = filterParam.get(ParamName.FIELD.getParamName());
        if (Objects.nonNull(direction) && Objects.nonNull(field)) {
            sb.append(ORDER_BY).append(field + SPACE).append(direction);
        }
        return sb.toString();
    }


}
