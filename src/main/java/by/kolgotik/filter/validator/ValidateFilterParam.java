package by.kolgotik.filter.validator;

import by.kolgotik.filter.annotation.ClientFilterName;
import by.kolgotik.filter.annotation.FilterParam;
import by.kolgotik.filter.exception.NoFilterParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public final class ValidateFilterParam {

    private static final Logger log = LoggerFactory.getLogger(ValidateFilterParam.class);
    private final ValidateFilterObject validateFilterObject = new ValidateFilterObject();
    private final ValidateForReserved validateForReserved = new ValidateForReserved();

    public void validate(Class<?> filterObjectClass) {

        if (validateFilterObject.validate(filterObjectClass)) {

            Set<Field> annotatedFields = Arrays.stream(filterObjectClass.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(FilterParam.class))
                    .collect(Collectors.toSet());

            if (annotatedFields.isEmpty()) {
                throw new NoFilterParamException("Class %s is annotated with @FilterObject and should have at least one @FilterParam".formatted(filterObjectClass.getName()));
            }
        }
    }

    public Map<String, String> validate(Class<?> filterObjectClass, Map<String, String> requestParams) {

        Map<String, String> validatedFilterParams = new HashMap<>();

        if (validateFilterObject.validate(filterObjectClass)) {

            Set<Field> annotatedFields = Arrays.stream(filterObjectClass.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(FilterParam.class))
                    .collect(Collectors.toSet());

            if (annotatedFields.isEmpty()) {
                throw new NoFilterParamException("Class %s is annotated with @FilterObject and should have at least one @FilterParam".formatted(filterObjectClass.getName()));
            }

            for (Field field : annotatedFields) {

                if (!field.isAnnotationPresent(ClientFilterName.class)) {

                    validateForReserved.validate(field.getAnnotation(FilterParam.class).columnName());

                    if (!requestParams.containsKey(field.getName())) {
                        log.warn("Field '{}' is not present in requestParams thus will not be used in filtering. " +
                                "\nYou can ignore this error if field '{}' was not passed in requestParams.", field.getName(), field.getName());
                    } else {
                        validatedFilterParams.put(field.getName(), requestParams.get(field.getName()));
                    }
                }
            }
        }
        return validatedFilterParams;
    }
}
