package by.kolgotik.filter.validator;

import by.kolgotik.filter.exception.InvalidFilterNameException;
import by.kolgotik.filter.sql.Reserved;

final class ValidateForReserved {

    void validate(String filterName) {

        if (filterName.contains(Reserved.COLUMN_NAME_AND_CLIENT_FILTER_NAME_SEPARATOR)) {
            throw new InvalidFilterNameException("Both @FilterParam and @ClientFilterParam must not contain this sequence: %s"
                    .formatted(Reserved.COLUMN_NAME_AND_CLIENT_FILTER_NAME_SEPARATOR));
        }

    }

}
