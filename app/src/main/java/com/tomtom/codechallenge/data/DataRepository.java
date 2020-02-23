package com.tomtom.codechallenge.data;

import androidx.lifecycle.LiveData;

import com.tomtom.codechallenge.data.db.DocumentDao;

import java.util.List;

public class DataRepository {

    private static DataRepository instance;

    private final DocumentDao documentDao;

    private DataRepository(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }

    public static DataRepository getInstance(DocumentDao documentDao) {
        if (instance == null) {
            synchronized (DataRepository.class) {
                if (instance == null) {
                    instance = new DataRepository(documentDao);
                }
            }
        }
        return instance;
    }

    public LiveData<List<Document>> getDocuments() {
        return documentDao.loadDocuments();
    }

    public LiveData<List<Document>> searchDocuments(String query) {
        return documentDao.searchDocuments(query);
    }

    public LiveData<Document> getDocument(String id) {
        return documentDao.loadDocument(id);
    }
}
