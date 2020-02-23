package com.tomtom.codechallenge.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tomtom.codechallenge.data.DataRepository;
import com.tomtom.codechallenge.data.Document;

public class DocumentDetailViewModel extends ViewModel {

//    private final DataRepository repository;
//    private final String documentId;
    private final LiveData<Document> document;

    public DocumentDetailViewModel(DataRepository repository, String documentId) {
//        this.repository = repository;
//        this.documentId = documentId;
        this.document = repository.getDocument(documentId);
    }

    public LiveData<Document> getDocument() {
        return document;
    }
}
