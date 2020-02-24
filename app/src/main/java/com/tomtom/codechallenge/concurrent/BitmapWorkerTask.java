package com.tomtom.codechallenge.concurrent;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.tomtom.codechallenge.data.DataRepository;
import com.tomtom.codechallenge.data.network.ApiResponse;
import com.tomtom.codechallenge.data.network.ApiService;

// This class was deprecated in API level R.
// Use the standard java.util.concurrent or Kotlin concurrency utilities instead.
public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

    private final DataRepository repository;
    private final ApiService apiService;
    private final TextView textView;

    public BitmapWorkerTask(DataRepository repository, ApiService apiService, TextView textView) {
        this.repository = repository;
        this.apiService = apiService;
        this.textView = textView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String isnbValue = strings[0];
        Bitmap bitmap = repository.getBitmapFromMemoryCache(isnbValue);
        if (bitmap != null) {
            return bitmap;
        }
        ApiResponse<Bitmap> response = apiService.getIsbnCoverImage(isnbValue);
        if (response.hasError()) {
            Log.e("BitmapWorkerTask", "Error GET Bitmap: " + response.getError());
            return null;
        }
        bitmap = response.getBody();
        repository.addBitmapToMemoryCache(isnbValue, bitmap);
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            BitmapDrawable bitmapDrawable = new BitmapDrawable(textView.getResources(), bitmap);
            textView.setCompoundDrawables(null, null, null, bitmapDrawable);
        }
    }
}
