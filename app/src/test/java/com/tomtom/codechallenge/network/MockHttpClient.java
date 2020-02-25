package com.tomtom.codechallenge.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomtom.codechallenge.data.Document;
import com.tomtom.codechallenge.data.network.ApiResponse;
import com.tomtom.codechallenge.data.network.DocumentDataHolder;
import com.tomtom.codechallenge.data.network.HttpClient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MockHttpClient implements HttpClient {

    private static final String TEST_DIRECTORY = "src/test/resources/api-response/";
    private static MockHttpClient instance;
    private String fileName = "";

    private MockHttpClient() { }

    @Override
    public ApiResponse<List<Document>> fetchDocuments(String requestUrl) {
        BufferedReader bufferedReader = null;
        try {
            String file = TEST_DIRECTORY + fileName;
            bufferedReader = new BufferedReader(new FileReader((file)));
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
            return ApiResponse.success(parseJsonResponse(stringBuilder.toString()));
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                    bufferedReader = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ApiResponse<Bitmap> loadCoveImage(String requestUrl) {
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        String file = TEST_DIRECTORY + fileName;
        Bitmap bitmap = BitmapFactory.decodeFile(file, decodeOptions);
        return ApiResponse.success(bitmap);
    }

    private List<Document> parseJsonResponse(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        DocumentDataHolder dataHolder = objectMapper.readValue(json, DocumentDataHolder.class);
        return dataHolder.getDocuments();
    }

    static MockHttpClient getInstance() {
        if (instance == null) {
            synchronized (MockHttpClient.class) {
                if (instance == null) {
                    instance = new MockHttpClient();
                }
            }
        }
        return instance;
    }

    void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
