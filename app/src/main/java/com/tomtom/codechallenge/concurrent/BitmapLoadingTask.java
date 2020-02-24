package com.tomtom.codechallenge.concurrent;

import android.graphics.Bitmap;
import android.util.Log;

import com.tomtom.codechallenge.data.DataRepository;
import com.tomtom.codechallenge.data.network.ApiResponse;

import java.util.concurrent.Callable;

public class BitmapLoadingTask implements Callable<Bitmap> {

    private final DataRepository repository;
    private final String value;
    private final String size;

    public BitmapLoadingTask(DataRepository repository, String value, String size) {
        this.repository = repository;
        this.value = value;
        this.size = size;
    }

    @Override
    public Bitmap call() throws Exception {
        Bitmap bitmap = repository.getBitmapFromMemoryCache(value);
        if (bitmap != null) {
            return bitmap;
        }
        ApiResponse<Bitmap> response = repository.getApiService().getIsbnCoverImage(value, size);
        if (response.hasError() || response.getBody() == null) {
            String errorMessage = response.hasError() ? response.getError() : "response.body IS null";
            Log.e("BitmapLoadingTask", "Error GET Bitmap: " + errorMessage);
            return null;
        }
        bitmap = response.getBody();
        repository.addBitmapToMemoryCache(value, bitmap);
        return bitmap;
    }
}
