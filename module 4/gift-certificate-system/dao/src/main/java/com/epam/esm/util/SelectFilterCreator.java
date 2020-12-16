package com.epam.esm.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SelectFilterCreator {
    private static final String SELECT_FILTER = "SELECT id, Name, Description, Price, CREATEDATE, " +
            "TimeZone_CreateDate, LastUpdateDate, TimeZone_LastUpdateDate, Duration " +
            "FROM (SELECT GiftCertificate.id,GiftCertificate.Name,GiftCertificate.Description,Price," +
            "CREATEDATE,TimeZone_CreateDate,LastUpdateDate,TimeZone_LastUpdateDate,Duration," +
            "group_concat(Tag.id , ' ' ,Tag.Name) as tag FROM Tag_has_GiftCertificate " +
            "RIGHT JOIN GiftCertificate ON Tag_has_GiftCertificate.GiftCertificate_id = " +
            "GiftCertificate.id LEFT JOIN Tag ON Tag_has_GiftCertificate.Tag_id = Tag.id " +
            "GROUP BY GiftCertificate.id) t";
    private static final String WHERE = " where ";
    private static final String AND = " and ";
    private static final String END_OF_LIKE = "%' ";
    private static final String LIKE = " like '%";
    private static final String ORDER_BY = " order by ";
    private static final String SPACE = " ";
    private static final List<String> wrongSearchParam = Arrays.asList(
            ParamName.DIRECTION.getParamName(),
            ParamName.FIELD.getParamName(),
            ParamName.TAG.getParamName());
    private static final String TAG = "tag";

    public String createFilterQuery(Map<String, String> filterParam) {
        StringBuilder sb = new StringBuilder(SELECT_FILTER);
        String searchQuery = searchingParams(filterParam);
        String tagQuery = tagParams(filterParam);
        String sortQuery = sortingParams(filterParam);
        if (!searchQuery.trim().isEmpty()) {
            sb.append(WHERE).append(searchQuery);
            sb.delete(sb.length() - AND.length(), sb.length());
        }
        if (!searchQuery.trim().isEmpty() && !tagQuery.trim().isEmpty()) {
            sb.append(tagQuery);
            sb.delete(sb.length() - AND.length(), sb.length());
        } else if (searchQuery.trim().isEmpty() && !tagQuery.trim().isEmpty()) {
            sb.append(WHERE).append(tagQuery);
            sb.delete(sb.length() - AND.length(), sb.length());
        }
        sb.append(sortQuery);
        return sb.toString();
    }


    private String tagParams(Map<String, String> filterParam) {
        StringBuilder sb = new StringBuilder();
        filterParam.entrySet().stream()
                .filter(e-> e.getKey().contains(ParamName.TAG.getParamName().toLowerCase()))
                .forEach(e -> {
                    sb.append(TAG);
                    sb.append(LIKE);
                    sb.append(e.getValue());
                    sb.append(END_OF_LIKE);
                    sb.append(AND);
                });
        return sb.toString();
    }

    private String searchingParams(Map<String, String> filterParam) {
        StringBuilder sb = new StringBuilder();
        filterParam.entrySet().stream()
                .filter(e-> wrongSearchParam.stream().noneMatch(w -> e.getKey().contains(w)))
                .forEach(e -> {
                    sb.append(e.getKey());
                    sb.append(LIKE);
                    sb.append(e.getValue());
                    sb.append(END_OF_LIKE);
                    sb.append(AND);
                });
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
