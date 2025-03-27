package by.kolgotik.filter.matcher;

import by.kolgotik.filter.annotation.FilterParam;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

final class FilterParamMatcher {

    private final ClientFilterNameMatcher clientFilterNameMatcher = new ClientFilterNameMatcher();

    public Map<String, String> match(Class<?> filterObjectClass, Map<String, String> requestParams) {

        Map<String, String> matchedFilterParams = new HashMap<>();

        Set<Field> annotatedFields = Arrays.stream(filterObjectClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(FilterParam.class))
                .collect(Collectors.toSet());

        for (Field field : annotatedFields) {

            String fieldName = field.getName();

            if (requestParams.containsKey(fieldName)) {

                String columnName = field.getAnnotation(FilterParam.class).columnName();

                matchedFilterParams.put(columnName, requestParams.get(fieldName));

            } else {

                Map<String, String> matched = clientFilterNameMatcher.match(filterObjectClass, requestParams);

                matchedFilterParams.putAll(matched);

            }

        }
        return matchedFilterParams;
    }
}
