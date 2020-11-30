package com.epam.esm.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@Component
public class FilterParamUtil {
    public static void deleteWrongSearchParam(Map<String, String> filterParam) {
        filterParam.entrySet().removeIf(
                entry -> Arrays.stream(ParamName.values()).noneMatch(e -> entry.getKey().contains(e.getParamName().toLowerCase()))
        );
    }

    public static void deleteWrongSortParam(Map<String, String> filterParam) {
        filterParam.entrySet().removeIf(
                entry -> ParamName.getPossibleDirectionParam().stream().noneMatch(e -> {
                    if (entry.getKey().equals(ParamName.DIRECTION.getParamName())) {
                        return entry.getValue().equals(e);
                    }
                    return true;
                })
        );
        filterParam.entrySet().removeIf(
                entry -> ParamName.getPossibleFieldParam().stream().noneMatch(e -> {
                    if (entry.getKey().equals(ParamName.FIELD.getParamName())) {
                        return entry.getValue().equals(e);
                    }
                    return true;
                })
        );
    }

    public static void putEqualParamsToMap(Map<String, String> map, Set<String> equalParams, ParamName name){
        String paramName = name.getParamName().toLowerCase();
        int i = 0;
        if(equalParams != null) {
            for (String ep : equalParams) {
                map.put(paramName + i++, ep);
            }
        }
        map.remove(paramName);
    }
}
