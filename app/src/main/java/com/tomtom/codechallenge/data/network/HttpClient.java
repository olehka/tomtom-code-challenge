package com.tomtom.codechallenge.data.network;

import android.graphics.Bitmap;

import com.tomtom.codechallenge.data.Document;

import java.util.List;

public interface HttpClient {

    ApiResponse<List<Document>> fetchDocuments(String requestUrl);

    ApiResponse<Bitmap> loadCoveImage(String requestUrl);

}
