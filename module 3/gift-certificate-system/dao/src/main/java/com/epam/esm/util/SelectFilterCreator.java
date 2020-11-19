package com.epam.esm.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SelectFilterCreator {
    private static final String SELECT_FILTER = "select * from GiftCertificate";
    private static final String HAVING = " having ";
    private static final String AND = " and ";
    private static final String END_OF_LIKE = "%' ";
    private static final String LIKE = " like '%";
    private static final String ORDER_BY = " order by ";
    private static final String SPACE = " ";
    private static final List<String> wrongPaginationParam = Arrays.asList(
            ParamName.DIRECTION.getParamName(),
            ParamName.FIELD.getParamName());

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
}
