package by.kolgotik.filter.sql.util.appender;


import by.kolgotik.filter.sql.util.separator.ClientNameSeparator;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Deprecated
final class ClientNameAndValueAppender {

    private static List<String> cleanKeys(Map<String, String> map) {

        return map
                .keySet()
                .stream()
                .filter(key -> key.contains("="))
                .map(key -> key.substring(0, key.indexOf("=")))
                .toList();
    }

    public static StringBuilder append(Map<String, String> requestParams) {

        Map<String, String> clientNamesAndValues = ClientNameSeparator.separate(requestParams);
        System.out.println("separated clientNamesAndValues = " + clientNamesAndValues);

        List<String> cleanedKeys = cleanKeys(clientNamesAndValues);
        System.out.println("cleaned keys = " + cleanedKeys);

        StringBuilder sb = new StringBuilder();

        Set<String> keySet = clientNamesAndValues.keySet();

        Iterator<String> clientNamesAndValuesIterator = keySet.iterator();

        while (clientNamesAndValuesIterator.hasNext()) {

            String key = clientNamesAndValuesIterator.next();

            sb.append(key).append(" = ").append(clientNamesAndValues.get(key));

            if (clientNamesAndValuesIterator.hasNext()) {
                sb.append(" AND ");
            }

        }
        return sb;
    }
}
