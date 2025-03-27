package by.kolgotik.filter.sql.util.separator;

import by.kolgotik.filter.sql.Reserved;

import java.util.*;
import java.util.stream.Collectors;

@Deprecated
public class ClientNameSeparator {

    public static Map<String, String> separate(Map<String, String> requestParams) {

        List<String> sortedWithSeparator = requestParams.keySet()
                .stream()
                .filter(key -> key.contains(Reserved.COLUMN_NAME_AND_CLIENT_FILTER_NAME_SEPARATOR))
                .toList();

        Map<String, String> separated = new HashMap<>();

        for (int i = 0; i < sortedWithSeparator.size(); i++) {

            String key = sortedWithSeparator.get(i);

            String leftSide = key.split(Reserved.COLUMN_NAME_AND_CLIENT_FILTER_NAME_SEPARATOR)[0];

            if (i + 1 != sortedWithSeparator.size()) {

                String next = sortedWithSeparator.get(i + 1).split(Reserved.COLUMN_NAME_AND_CLIENT_FILTER_NAME_SEPARATOR)[0];

                if (leftSide.equals(next)) {

                    separated.put(leftSide, requestParams.get(key));
                    separated.put(next+"="+requestParams.get(key), requestParams.get(key));

                }

            }
            separated.put(leftSide, requestParams.get(key));
        }
        return separated;
    }

    public static List<String> separateOnlyClientNames(Map<String, String> requestParams) {

        Set<String> sortedWithoutSeparator = requestParams.keySet()
                .stream()
                .filter(key -> !key.contains(Reserved.COLUMN_NAME_AND_CLIENT_FILTER_NAME_SEPARATOR))
                .collect(Collectors.toSet());

        Set<String> sortedWithSeparator = requestParams.keySet()
                .stream()
                .filter(key -> key.contains(Reserved.COLUMN_NAME_AND_CLIENT_FILTER_NAME_SEPARATOR))
                .collect(Collectors.toSet());

        List<String> separated = new ArrayList<>();

        for (String key : sortedWithSeparator) {

            String rightSide = key.split(Reserved.COLUMN_NAME_AND_CLIENT_FILTER_NAME_SEPARATOR)[1];
            separated.add(rightSide);

        }

        return separated;

    }

}
