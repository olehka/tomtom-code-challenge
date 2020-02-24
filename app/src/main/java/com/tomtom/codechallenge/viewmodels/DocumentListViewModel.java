package com.tomtom.codechallenge.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tomtom.codechallenge.data.DataRepository;
import com.tomtom.codechallenge.data.Document;

import java.util.List;

public class DocumentListViewModel extends ViewModel {

    private final DataRepository repository;
    private final LiveData<List<Document>> documents;

    DocumentListViewModel(@NonNull DataRepository repository) {
        this.repository = repository;
        this.documents = repository.getAllDocuments();
    }

    public LiveData<List<Document>> getDocuments() {
        return documents;
    }

    public void searchByQuery(String query) {
        repository.fetchDocumentsByQuery(query);
    }

    public void searchByTitle(String title) {
        repository.fetchDocumentsByTitle(title);
    }

    public void searchByAuthor(String author) {
        repository.fetchDocumentsByAuthor(author);
    }
}
