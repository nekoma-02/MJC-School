package com.epam.esm;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Map;

public class UpdateCreator<T> {

    private ObjectMapper mapper;
    private T object;

    public T getObject() {
        return object;
    }

    public UpdateCreator(T object, ObjectMapper mapper) {
        this.object = object;
        this.mapper = mapper;
    }

    public Map<String, Object> getConvertedMap() {
        return convertObjectToMap();
    }

    private Map<String, Object> convertObjectToMap() {
        Map<String, Object> map = mapper.convertValue(this.object, Map.class);
        map.values().removeAll(Arrays.asList(null, 0L));
        return map;
    }

    private String createSqlUpdateQuery(Object object) {
        Map<String, Object> map = convertObjectToMap();
        StringBuilder sb = new StringBuilder("Update " + object.getClass().getSimpleName() + " set ");

        for (Map.Entry entry : map.entrySet()) {
            if (!entry.getKey().equals("id")) {
                sb.append(entry.getKey() + " = :" + entry.getKey() + ", ");
            }
        }
        sb.deleteCharAt(sb.length()-2);
        sb.append(" where id = :id");

        return sb.toString();
    }

    public String getSqlUpdateQuery(Object object) {
        return createSqlUpdateQuery(object);
    }

}
