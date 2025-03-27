package by.kolgotik.filter.sql.util.appender;

import by.kolgotik.filter.sql.Reserved;

import java.util.Iterator;
import java.util.Map;

public class DefaultAppender {

    public static StringBuilder append(Map<String, String> requestParams) {

        StringBuilder sb = new StringBuilder();

        for (Iterator<Map.Entry<String, String>> iterator = requestParams.entrySet().iterator(); iterator.hasNext(); ) {

            Map.Entry<String, String> entry = iterator.next();

            String key = entry.getKey();
            String value = entry.getValue();

            if (key.contains(Reserved.COLUMN_NAME_AND_CLIENT_FILTER_NAME_SEPARATOR)) {

                String[] parts = key.split(Reserved.COLUMN_NAME_AND_CLIENT_FILTER_NAME_SEPARATOR);

                String columnName = parts[0];

                sb.append(columnName).append(" = ").append(value);

                if (iterator.hasNext()) {
                    sb.append(" AND ");
                }
            }

            if (!key.contains(Reserved.COLUMN_NAME_AND_CLIENT_FILTER_NAME_SEPARATOR)) {

                sb.append(key).append(" = ").append(value);

                if (iterator.hasNext()) {
                    sb.append(" AND ");
                }

            }

        }
        return sb;
    }
}
