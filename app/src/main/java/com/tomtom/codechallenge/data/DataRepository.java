package com.tomtom.codechallenge.data;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;

import androidx.lifecycle.LiveData;

import com.tomtom.codechallenge.concurrent.AppExecutors;
import com.tomtom.codechallenge.data.db.DocumentDao;
import com.tomtom.codechallenge.data.network.ApiResponse;
import com.tomtom.codechallenge.data.network.ApiService;

import java.util.List;

public class DataRepository {

    private static DataRepository instance;

    private final DocumentDao documentDao;
    private final ApiService apiService;
    private final AppExecutors appExecutors;

    //developer.android.com/topic/performance/graphics/cache-bitmap
    private LruCache<String, Bitmap> memoryCache;

    private DataRepository(DocumentDao documentDao, ApiService apiService, AppExecutors appExecutors) {
        this.documentDao = documentDao;
        this.apiService = apiService;
        this.appExecutors = appExecutors;
        initMemoryCache();
    }

    private void initMemoryCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        memoryCache = new LruCache<String, Bitmap>(maxMemory / 8) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    public static DataRepository getInstance(DocumentDao documentDao, ApiService apiService, AppExecutors appExecutors) {
        if (instance == null) {
            synchronized (DataRepository.class) {
                if (instance == null) {
                    instance = new DataRepository(documentDao, apiService, appExecutors);
                }
            }
        }
        return instance;
    }

    public LiveData<Document> getDocumentById(String id) {
        return documentDao.getDocument(id);
    }

    public LiveData<List<Document>> getAllDocuments() {
        return documentDao.getAllDocuments();
    }

    public LiveData<FetchResult> fetchDocumentsByQuery(String query) {
        deleteAllDocuments();
        FetchDocumentTask task = new FetchDocumentTask(documentDao) {
            @Override
            ApiResponse<List<Document>> fetchDocuments() {
                return apiService.getDocumentsByQuery(query);
            }
        };
        appExecutors.networkIO().execute(task);
        return task.getResultLiveData();
    }

    public LiveData<FetchResult> fetchDocumentsByTitle(String title) {
        deleteAllDocuments();
        FetchDocumentTask task = new FetchDocumentTask(documentDao) {
            @Override
            ApiResponse<List<Document>> fetchDocuments() {
                return apiService.getDocumentsByTitle(title);
            }
        };
        appExecutors.networkIO().execute(task);
        return task.getResultLiveData();
    }

    public LiveData<FetchResult> fetchDocumentsByAuthor(String author) {
        deleteAllDocuments();
        FetchDocumentTask task = new FetchDocumentTask(documentDao) {
            @Override
            ApiResponse<List<Document>> fetchDocuments() {
                return apiService.getDocumentsByAuthor(author);
            }
        };
        appExecutors.networkIO().execute(task);
        return task.getResultLiveData();
    }

    private void deleteAllDocuments() {
        appExecutors.diskIO().execute(() -> {
            Log.d("DataRepository", "Delete all documents");
            documentDao.deleteAllDocuments();
        });
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemoryCache(String key) {
        return memoryCache.get(key);
    }

    public ApiService getApiService() {
        return apiService;
    }

    public AppExecutors getAppExecutors() {
        return appExecutors;
    }
}
