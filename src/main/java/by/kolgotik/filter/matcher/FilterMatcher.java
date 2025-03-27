package by.kolgotik.filter.matcher;

import by.kolgotik.filter.validator.FilterValidator;

import java.util.Map;

public final class FilterMatcher {

    private final FilterParamMatcher filterParamMatcher = new FilterParamMatcher();
    private final FilterValidator filterValidator = new FilterValidator();

    public Map<String, String> match(Class<?> filterObjectClass, Map<String, String> requestParams) {

        Map<String, String> validated = filterValidator.validate(filterObjectClass, requestParams);

        return filterParamMatcher.match(filterObjectClass, validated);

    }
}