package com.dvtsoftware.stocktrade.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ResultMatcher {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static List<JsonNode> parseJsonArray(String jsonString) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, new TypeReference<List<JsonNode>>() {});
        } catch (IOException ex) {
            return null;
        }
    }

    private static List<JsonNode> sortJsonArray(List<JsonNode> jsonArray) {
        return jsonArray.stream().sorted(Comparator.comparing(JsonNode::toString)).collect(Collectors.toList());
    }

    private static boolean preliminaryJsonArrayMatching(List<JsonNode> response, List<JsonNode> expected) {
        if (response == null || expected == null) return false;
        return response.size() == expected.size();
    }

    private static boolean jsonArrayMatching(List<JsonNode> response, List<JsonNode> expected, boolean reportMismatch) {
        for (int i = 0; i < response.size(); i++) {
            JsonNode expectedJson = expected.get(i);
            JsonNode responseJson = response.get(i);
            if (!expectedJson.equals(responseJson)) {
                if (reportMismatch) {
                    System.out.println(Color.RED + "Expected <" + expectedJson.toString() + "> but was <" + responseJson.toString() + "> (at index " + i + ")." + Color.RESET);
                }
                return false;
            }
        }
        return true;
    }

    public static boolean matchJsonArrayIgnoreOrder(String responseString, String expectedString, boolean reportMismatch) {
        List<JsonNode> response = parseJsonArray(responseString);
        List<JsonNode> expected = parseJsonArray(expectedString);
        if (!preliminaryJsonArrayMatching(response, expected)) return false;
        return jsonArrayMatching(sortJsonArray(response), sortJsonArray(expected), reportMismatch);
    }

    public static boolean matchJsonArray(String responseString, String expectedString, boolean reportMismatch) {
        List<JsonNode> response = parseJsonArray(responseString);
        List<JsonNode> expected = parseJsonArray(expectedString);
        if (!preliminaryJsonArrayMatching(response, expected)) return false;
        return jsonArrayMatching(response, expected, reportMismatch);
    }

    public static boolean matchJson(String responseString, String expectedString, boolean reportMismatch) {
        try {
            JsonNode response = OBJECT_MAPPER.readTree(responseString);
            JsonNode expected = OBJECT_MAPPER.readTree(expectedString);
            if (!response.equals(expected)) {
                if (reportMismatch) {
                    System.out.println(Color.RED + "Expected <" + expectedString + "> but was <" + responseString + ">." + Color.RESET);
                }
                return false;
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}
