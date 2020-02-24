package com.tomtom.codechallenge.utilities;

public class NetworkUtil {

    public static String buildGetRequestUrl(String baseUrl, String key, String value) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(baseUrl).append("?");
        stringBuilder.append(key).append("=");
        stringBuilder.append(processRequestParams(value));
        return stringBuilder.toString();
    }

    public static String processRequestParams(String value) {
        String[] words = value
                .toLowerCase()
                .replaceAll("[^a-zA-Z0-9\\s+]", "")
                .split("\\s");
        StringBuilder stringBuilder = new StringBuilder();
        for (String word: words) {
            stringBuilder.append(word);
            stringBuilder.append("+");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public static String buildCoveImageUrl(String baseUrl, String key, String value, String size) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(baseUrl);
        stringBuilder.append(key).append("/");
        stringBuilder.append(value).append("-");
        stringBuilder.append(size).append(".jpg");
        return stringBuilder.toString();
    }
}
