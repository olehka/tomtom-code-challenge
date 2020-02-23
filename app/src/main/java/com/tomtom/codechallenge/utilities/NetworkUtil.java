package com.tomtom.codechallenge.utilities;

public class NetworkUtil {

    public static String buildRequestUrl(String baseUrl, String key, String value) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(baseUrl).append("?");
        stringBuilder.append(key).append("=");
        stringBuilder.append(processParameters(value));
        return stringBuilder.toString();
    }

    public static String processParameters(String value) {
        String[] words = value.split("\\s");
        StringBuilder stringBuilder = new StringBuilder();
        for (String word: words) {
            stringBuilder.append(word);
            stringBuilder.append("+");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
