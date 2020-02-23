package com.tomtom.codechallenge.data;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DataRepository {

    private static DataRepository instance;

    public static DataRepository getInstance() {
        if (instance == null) {
            synchronized (DataRepository.class) {
                if (instance == null) {
                    instance = new DataRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<Document>> getDocuments() {
        return null;
    }

    public LiveData<List<Document>> searchDocuments(String query) {
        return null;
    }

    public LiveData<Document> getDocument(String id) {
        return null;
    }
}
