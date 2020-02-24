package com.tomtom.codechallenge.data;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.tomtom.codechallenge.AppExecutors;
import com.tomtom.codechallenge.data.db.DocumentDao;
import com.tomtom.codechallenge.data.network.ApiResponse;
import com.tomtom.codechallenge.data.network.ApiService;

import java.util.List;

public class DataRepository {

    private static DataRepository instance;

    private final DocumentDao documentDao;
    private final ApiService apiService;
    private final AppExecutors appExecutors;

    private DataRepository(DocumentDao documentDao, ApiService apiService, AppExecutors appExecutors) {
        this.documentDao = documentDao;
        this.apiService = apiService;
        this.appExecutors = appExecutors;
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

    public LiveData<List<Document>> searchDocumentsByQuery(String query) {
        return documentDao.loadAllDocuments();
    }

    public LiveData<List<Document>> searchDocumentsByTitle(String title) {
        return documentDao.searchDocumentsByTitle(title);
    }

    public LiveData<List<Document>> searchDocumentsByAuthor(String author) {
        return documentDao.searchDocumentsByAuthor(author);
    }

    public LiveData<Document> getDocumentById(String id) {
        return documentDao.loadDocument(id);
    }

    public LiveData<List<Document>> getAllDocumentsFromDb() {
        return documentDao.loadAllDocuments();
    }

    public void fetchDocumentsByQueryFromNetwork(String query) {
        appExecutors.networkIO().execute(() -> {
            ApiResponse<List<Document>> response = apiService.getDocumentsByQuery(query);
            if (response.hasError()) {
                Log.e("DataRepository", "Network error: " + response.getError());
            } else {
                List<Document> documentList = response.getBody();
                appExecutors.diskIO().execute(() -> documentDao.saveDocuments(documentList));
            }
        });
    }
}
