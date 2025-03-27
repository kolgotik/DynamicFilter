package by.kolgotik.filter;



import by.kolgotik.filter.matcher.FilterMatcher;
import by.kolgotik.filter.sql.imp.SimpleSqlBuilder;
import by.kolgotik.filter.test.UserFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class FilterApplication {

    private static final Logger log = LoggerFactory.getLogger(FilterApplication.class);

    public static void main(String[] args) {

        UserFilter userFilter = new UserFilter("Jo", 33, 60,"123", "jo@gmail.com");

        Map<String, String> requestParams = Map.of(
                "name", userFilter.getName(),
                "age", String.valueOf(userFilter.getAge()),
                "pass", userFilter.getPassword(),
                "email", userFilter.getEmail());

        Map<String, String> requestParams1 = Map.of(
                "username", userFilter.getName(),
                "age-max", String.valueOf(userFilter.getAge()),
                "pass", userFilter.getPassword());

        Map<String, String> requestParams2 = Map.of(
                "username", userFilter.getName(),
                "age", String.valueOf(userFilter.getAge()));

        Map<String, String> requestParams3 = Map.of(
                "username", userFilter.getName());

        Map<String, String> requestParams4 = Map.of();

        Map<String, String> requestParams5 = Map.of(
                "usernames", userFilter.getName(),
                "age-min", String.valueOf(userFilter.getAge()),
                "age-max", String.valueOf(userFilter.getAge() + 20),
                "password", userFilter.getPassword(),
                "email", userFilter.getEmail());

        FilterMatcher filterMatcher = new FilterMatcher();
        Map<String, String> matchedRequestParams = filterMatcher.match(UserFilter.class, requestParams5);

        log.info("Matched: {}", matchedRequestParams);

        SimpleSqlBuilder simpleSqlBuilder = new SimpleSqlBuilder();
        String builtQuery = simpleSqlBuilder
                .select(UserFilter.class, matchedRequestParams)
                .from(UserFilter.class)
                .where(UserFilter.class, matchedRequestParams)
                .build();

        log.info("Built query: {}\n\n", builtQuery);
    }

}
