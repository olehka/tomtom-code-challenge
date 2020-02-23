package com.tomtom.codechallenge.data.network;

import com.tomtom.codechallenge.data.Document;

import java.util.List;

public interface ApiService {

    String SEARCH_URL = "http://openlibrary.org/search.json";
    String PARAM_QUERY_KEY = "q";
    String PARAM_TITLE_KEY = "title";
    String PARAM_AUTHOR_KEY = "author";

    ApiResponse<List<Document>> getDocumentsByQuery(String query);

    ApiResponse<List<Document>> getDocumentsByTitle(String title);

    ApiResponse<List<Document>> getDocumentsByAuthor(String author);
}
