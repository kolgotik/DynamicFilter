package by.kolgotik.filter.sql;

import java.util.Map;

public interface DQLSqlBuilder extends SqlBuilder {

    DQLSqlBuilder select(Class<?> filterObject, Map<String, String> validatedAndMatchedRequestParams);

    DQLSqlBuilder from(Class<?> filterObject);

    DQLSqlBuilder where(Class<?> filterObject, Map<String, String> validatedAndMatchedRequestParams);

    DQLSqlBuilder groupBy(String column);

    DQLSqlBuilder orderBy(String column);

}
