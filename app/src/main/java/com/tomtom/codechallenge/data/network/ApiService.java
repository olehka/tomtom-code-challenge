package com.tomtom.codechallenge.data.network;

import android.graphics.Bitmap;

import com.tomtom.codechallenge.data.Document;

import java.util.List;

public interface ApiService {

    String DOC_SEARCH_URL = "https://openlibrary.org/search.json";
    String ISBN_IMAGE_URL = "https://covers.openlibrary.org/b/";

    String PARAM_QUERY_KEY = "q";
    String PARAM_TITLE_KEY = "title";
    String PARAM_AUTHOR_KEY = "author";

    String KEY_ISBN = "isbn";
    String IMAGE_SIZE_SMALL = "S";
    String IMAGE_SIZE_MEDIUM = "M";
    String IMAGE_SIZE_LARGE = "L";

    ApiResponse<List<Document>> getDocumentsByQuery(String query);

    ApiResponse<List<Document>> getDocumentsByTitle(String title);

    ApiResponse<List<Document>> getDocumentsByAuthor(String author);

    ApiResponse<Bitmap> getIsbnCoverImage(String isbnValue, String size);
}
