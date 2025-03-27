package by.kolgotik.filter.sql.imp;


import by.kolgotik.filter.annotation.FilterObject;
import by.kolgotik.filter.sql.DQLSqlBuilder;
import by.kolgotik.filter.sql.Reserved;
import by.kolgotik.filter.sql.util.appender.DefaultAppender;
import by.kolgotik.filter.validator.ValidateFilterObject;
import by.kolgotik.filter.validator.ValidateFilterParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class SimpleSqlBuilder implements DQLSqlBuilder {

    private static final Logger log = LoggerFactory.getLogger(SimpleSqlBuilder.class);
    private final StringBuilder query = new StringBuilder();
    private final ValidateFilterObject validateFilterObject = new ValidateFilterObject();
    private final ValidateFilterParam validateFilterParam = new ValidateFilterParam();

    @Override
    public DQLSqlBuilder select(Class<?> filterObject, Map<String, String> validatedAndMatchedRequestParams) {

        validateRequestParams(validatedAndMatchedRequestParams);

        validateFilterParam.validate(filterObject);

        query.append("SELECT ");

        Set<String> cleared = new HashSet<>();

        for (String key : validatedAndMatchedRequestParams.keySet()) {

            if (key.contains(Reserved.COLUMN_NAME_AND_CLIENT_FILTER_NAME_SEPARATOR)) {
                String clearedKey = key.substring(0, key.indexOf(Reserved.COLUMN_NAME_AND_CLIENT_FILTER_NAME_SEPARATOR));
                cleared.add(clearedKey);
            } else {
                cleared.add(key);
            }
        }

        Iterator<String> iterator = cleared.iterator();

        while (iterator.hasNext()) {

            String column = iterator.next();

            query.append(column);

            if (iterator.hasNext()) {
                query.append(", ");
            }
        }
        return this;
    }


    @Override
    public DQLSqlBuilder from(Class<?> filterObject) {

        validateFilterObject.validate(filterObject);

        query.append(" FROM ").append(filterObject.getAnnotation(FilterObject.class).tableName());

        return this;
    }

    @Override
    public DQLSqlBuilder where(Class<?> filterObject, Map<String, String> validatedAndMatchedRequestParams) {

        //TODO: To make life easier, there should be some kind of naming convention for clauses which are used in filtering.
        // for example: to mark by.kolgotik.filter value as minimal value it should have '-min' suffix, for ascending value its '-asc' and so on.
        // there should be a configurable class with default conventions for clauses.
        // when building the query 'validatedAndMatchedRequestParams' should be validated for correct conventions.


        //TODO: Also the 'where' clause is basically the main component in building the query.
        // so it should have classes with logic to build queries depending on the existing clauses from the requestParams.

        //TODO: Maybe make Clause ENUM or Class with static fields of clauses



        //TODO: this ( "user-age = 33 AND user-age = 53" ) will not work because of '=' it should be changed to '>=' and '<='
        // to make it work it should consider and apply conventions, so should make convention processor
        // Also string values should be in quotes

        StringBuilder appended = DefaultAppender.append(validatedAndMatchedRequestParams);
        log.info("All appended params are: {}", appended);

        validateRequestParams(validatedAndMatchedRequestParams);

        query
                .append(" WHERE ")
                .append(appended);

        return this;
    }

    @Override
    public DQLSqlBuilder groupBy(String column) {

        query.append(" GROUP BY ").append(column);
        return this;
    }

    @Override
    public DQLSqlBuilder orderBy(String column) {

        query.append(" ORDER BY ").append(column);
        return this;
    }

    @Override
    public String build() {
        return query.toString();
    }

    private void validateRequestParams(Map<String, String> validatedAndMatchedRequestParams) {

        if (validatedAndMatchedRequestParams == null) {
            throw new IllegalArgumentException("Request params can't be null");
        }
        if (validatedAndMatchedRequestParams.isEmpty()) {
            throw new IllegalArgumentException("Request params can't be empty");
        }

    }
}
