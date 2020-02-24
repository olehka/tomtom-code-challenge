package com.tomtom.codechallenge.data.network;

import android.graphics.Bitmap;

import com.tomtom.codechallenge.data.Document;
import com.tomtom.codechallenge.utilities.NetworkUtil;

import java.util.List;

public class ApiServiceImpl implements ApiService {

    private static ApiService instance;

    private HttpClient httpClient;

    private ApiServiceImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public ApiResponse<List<Document>> getDocumentsByQuery(String query) {
        return httpClient.fetchDocuments(
                NetworkUtil.buildGetRequestUrl(DOC_SEARCH_URL, PARAM_QUERY_KEY, query));
    }

    @Override
    public ApiResponse<List<Document>> getDocumentsByTitle(String title) {
        return httpClient.fetchDocuments(
                NetworkUtil.buildGetRequestUrl(DOC_SEARCH_URL, PARAM_TITLE_KEY, title));
    }

    @Override
    public ApiResponse<List<Document>> getDocumentsByAuthor(String author) {
        return httpClient.fetchDocuments(
                NetworkUtil.buildGetRequestUrl(DOC_SEARCH_URL, PARAM_AUTHOR_KEY, author));
    }

    @Override
    public ApiResponse<Bitmap> getIsbnCoverImage(String isbnValue, String size) {
        return httpClient.loadCoveImage(
                NetworkUtil.buildCoveImageUrl(ISBN_IMAGE_URL, KEY_ISBN, isbnValue, size));
    }

    public static ApiService getInstance(HttpClient httpClient) {
        if (instance == null) {
            synchronized (ApiServiceImpl.class) {
                if (instance == null) {
                    instance = new ApiServiceImpl(httpClient);
                }
            }
        }
        return instance;
    }
}
