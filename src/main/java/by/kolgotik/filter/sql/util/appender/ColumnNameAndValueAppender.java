package by.kolgotik.filter.sql.util.appender;

import by.kolgotik.filter.sql.util.separator.ColumnNameSeparator;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Deprecated
final class ColumnNameAndValueAppender {

    public static StringBuilder append(Map<String, String> requestParams) {

        Map<String, String> columnNamesAndValues = ColumnNameSeparator.separate(requestParams);
        System.out.println("separated columnNamesAndValues = " + columnNamesAndValues);

        StringBuilder sb = new StringBuilder();

        Set<String> keySet = columnNamesAndValues.keySet();

        Iterator<String> columnNamesAndValuesIterator = keySet.iterator();

        while (columnNamesAndValuesIterator.hasNext()) {

            String key = columnNamesAndValuesIterator.next();

            sb.append(key).append(" = ").append(columnNamesAndValues.get(key));

            if (columnNamesAndValuesIterator.hasNext()) {
                sb.append(" AND ");
            }

        }
        return sb;
    }

}
