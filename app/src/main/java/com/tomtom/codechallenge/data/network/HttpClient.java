package com.tomtom.codechallenge.data.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomtom.codechallenge.data.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HttpClient {

    private static HttpClient instance;

    private static final int TIMEOUT_5_SEC = 5000;

    private static final String REQUEST_METHOD_GET = "GET";

    private HttpClient() {}

    ApiResponse<List<Document>> fetchDocuments(String requestUrl) {
        HttpURLConnection httpURLConnection = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(requestUrl);
            httpURLConnection = createConnection(url);
            int code = httpURLConnection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null) {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
                return ApiResponse.success(parseJsonResponse(stringBuilder.toString()));
            } else {
                return ApiResponse.error("Error GET Request, Status-Code: " + code);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage());
        } finally {
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                    inputStreamReader = null;
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                    bufferedReader = null;
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                    httpURLConnection = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private HttpURLConnection createConnection(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(REQUEST_METHOD_GET);
        httpURLConnection.setConnectTimeout(TIMEOUT_5_SEC);
        httpURLConnection.setReadTimeout(TIMEOUT_5_SEC);
        httpURLConnection.connect();
        return httpURLConnection;
    }

    private List<Document> parseJsonResponse(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonDocumentDataHolder dataHolder = objectMapper.readValue(json, JsonDocumentDataHolder.class);
        return dataHolder.documents;
    }

    public static HttpClient getInstance() {
        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    instance = new HttpClient();
                }
            }
        }
        return instance;
    }
}
