package by.kolgotik.filter.validator;

import java.util.Map;

public final class FilterValidator {

    private final ValidateClientFilterName validateClientFilterName = new ValidateClientFilterName();
    private final ValidateFilterParam validateFilterParam = new ValidateFilterParam();

    public Map<String, String> validate(Class<?> filterObjectClass, Map<String, String> requestParams) {

        Map<String, String> validatedFilterParams = validateFilterParam.validate(filterObjectClass, requestParams);

        Map<String, String> validatedClientFilterName = validateClientFilterName.validate(filterObjectClass, requestParams);

        validatedClientFilterName.putAll(validatedFilterParams);

        return validatedClientFilterName;
    }
}
