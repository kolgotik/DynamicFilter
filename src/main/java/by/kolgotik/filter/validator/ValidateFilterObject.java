package by.kolgotik.filter.validator;

import by.kolgotik.filter.annotation.FilterObject;
import by.kolgotik.filter.exception.NotFilterException;

public final class ValidateFilterObject {
    public boolean validate(Class<?> filterObjectClass) {

        if (!(filterObjectClass.isAnnotationPresent(FilterObject.class))) {
            throw new NotFilterException("Class: %s is not annotated with @FilterObject".formatted(filterObjectClass.getName()));
        } else {
            return true;
        }
    }
}
