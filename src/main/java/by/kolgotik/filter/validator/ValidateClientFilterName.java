package by.kolgotik.filter.validator;

import by.kolgotik.filter.annotation.ClientFilterName;
import by.kolgotik.filter.annotation.FilterParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

final class ValidateClientFilterName {

    private static final Logger log = LoggerFactory.getLogger(ValidateClientFilterName.class);
    private final ValidateForReserved validateForReserved = new ValidateForReserved();

    public Map<String, String> validate(Class<?> filterObjectClass, Map<String, String> requestParams) {

        Map<String, String> validatedRequestParams = new HashMap<>();

        Set<Field> annotatedFields = Arrays.stream(filterObjectClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ClientFilterName.class))
                .collect(Collectors.toSet());

        for (Field field : annotatedFields) {

            String clientFilterNameValue = field.getAnnotation(ClientFilterName.class).name();
            validateForReserved.validate(clientFilterNameValue);

            if (requestParams.containsKey(clientFilterNameValue)) {

                if (field.isAnnotationPresent(FilterParam.class)) {

                    String columnName = field.getAnnotation(FilterParam.class).columnName();
                    validatedRequestParams.put(clientFilterNameValue, requestParams.get(clientFilterNameValue));

                } else {
                    log.warn("Field '{}' of class {} is not annotated with @FilterParam thus will not be used in filtering", field.getName(), filterObjectClass.getName());
                }
            }

            if (!(requestParams.containsKey(clientFilterNameValue))) {
                log.warn("Expected name: '{}' for @ClientFilterName of class {} thus will not be used in filtering." +
                        "\nYou can ignore this error if expected name was not passed in requestParams.", clientFilterNameValue, filterObjectClass.getName());
            }
        }
        return validatedRequestParams;
    }
}
