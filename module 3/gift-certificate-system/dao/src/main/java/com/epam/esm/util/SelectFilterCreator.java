package com.epam.esm.util;

import java.util.Arrays;
import java.util.List;
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
    private static final String LIMIT = " limit ";
    private static final String OFFSET = " offset ";
    private static final List<String> wrongPaginationParam = Arrays.asList(
            ParamName.DIRECTION.getParamName(),
            ParamName.FIELD.getParamName(),
            ParamName.LIMIT.getParamName(),
            ParamName.OFFSET.getParamName());

    public String createFilterQuery(Map<String, String> filterParam) {
        StringBuilder sb = new StringBuilder(SELECT_FILTER);
        String searchQuery = searchingParams(filterParam);
        String sortQuery = sortingParams(filterParam);
        String paginationQuery = paginationParams(filterParam);
        if (!searchQuery.trim().isEmpty()) {
            sb.append(HAVING).append(searchQuery);
            sb.delete(sb.length() - AND.length(), sb.length());
        }
        sb.append(sortQuery);
        sb.append(paginationQuery);
        //delete comment
        System.out.println("query = "+sb.toString());
        return sb.toString();
    }

    private String searchingParams(Map<String, String> filterParam) {
        StringBuilder sb = new StringBuilder();
        filterParam.entrySet().stream()
                .filter(e-> wrongPaginationParam.stream().noneMatch(w -> e.getKey().equals(w)))
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

    private String paginationParams(Map<String, String> filterParam) {
        StringBuilder sb = new StringBuilder();
        String limit = filterParam.get(ParamName.LIMIT.getParamName());
        String offset = filterParam.get(ParamName.OFFSET.getParamName());
        if (Objects.nonNull(limit) && Objects.nonNull(offset)) {
            sb.append(LIMIT).append(limit + SPACE).append(OFFSET).append(offset);
        }
        return sb.toString();
    }
}
