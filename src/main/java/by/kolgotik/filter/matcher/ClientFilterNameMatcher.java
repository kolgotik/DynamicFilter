package by.kolgotik.filter.matcher;

import by.kolgotik.filter.annotation.ClientFilterName;
import by.kolgotik.filter.annotation.FilterParam;
import by.kolgotik.filter.sql.Reserved;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

final class ClientFilterNameMatcher {

    public Map<String, String> match(Class<?> filterObjectClass, Map<String, String> requestParams) {

        Map<String, String> matchedFilterParams = new HashMap<>();

        Set<Field> annotatedFields = Arrays.stream(filterObjectClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ClientFilterName.class))
                .collect(Collectors.toSet());

        for (Field field : annotatedFields) {

            String clientFilterNameValue = field.getAnnotation(ClientFilterName.class).name();

            if (requestParams.containsKey(clientFilterNameValue)) {

                if (field.isAnnotationPresent(FilterParam.class)) {

                    if (!field.getName().equals(clientFilterNameValue)) {

                        String columnName = field.getAnnotation(FilterParam.class).columnName();
                        matchedFilterParams.put(
                                columnName + Reserved.COLUMN_NAME_AND_CLIENT_FILTER_NAME_SEPARATOR + clientFilterNameValue,
                                requestParams.get(clientFilterNameValue)
                        );
                    }
                }
            }
        }
        return matchedFilterParams;
    }
}
