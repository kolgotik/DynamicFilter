package by.kolgotik.filter.service;

import by.kolgotik.filter.sql.SqlBuilder;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class FilterService {

    private final SqlBuilder sqlBuilder;

    public String filterFor(Class<?> clazz, Map<String, String> requestParams) {
        return sqlBuilder.build();
    }

}
