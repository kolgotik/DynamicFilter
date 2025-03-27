package by.kolgotik.filter.sql.util.separator;

import by.kolgotik.filter.sql.Reserved;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Deprecated
public class ColumnNameSeparator {

    public static Map<String, String> separate(Map<String, String> requestParams) {

        Set<String> sorted = requestParams.keySet()
                .stream()
                .filter(key -> !key.contains(Reserved.COLUMN_NAME_AND_CLIENT_FILTER_NAME_SEPARATOR))
                .collect(Collectors.toSet());

        Map<String, String> separated = new HashMap<>();

        for (String key : sorted) {

            separated.put(key, requestParams.get(key));

        }
        return separated;
    }

    public static List<String> separateOnlyColumnNames(Map<String, String> requestParams) {

        return requestParams.keySet()
                .stream()
                .filter(key -> !key.contains(Reserved.COLUMN_NAME_AND_CLIENT_FILTER_NAME_SEPARATOR))
                .distinct()
                .collect(Collectors.toList());

    }

}
