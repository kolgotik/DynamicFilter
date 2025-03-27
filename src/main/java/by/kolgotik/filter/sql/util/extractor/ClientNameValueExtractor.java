package by.kolgotik.filter.sql.util.extractor;

import by.kolgotik.filter.sql.Reserved;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Deprecated
public class ClientNameValueExtractor {

    public static List<String> extractValue(Map<String, String> requestParams, List<String> clientNames) {

        Set<String> keySet = requestParams.keySet();

        List<String> extractedValues = new ArrayList<>();

        for (String key : keySet) {

            if (key.contains(Reserved.COLUMN_NAME_AND_CLIENT_FILTER_NAME_SEPARATOR)) {
                if (clientNames.contains(key.split(Reserved.COLUMN_NAME_AND_CLIENT_FILTER_NAME_SEPARATOR)[1])) {

                    extractedValues.add(requestParams.get(key));

                }
            }
        }
        return extractedValues;
    }
}
