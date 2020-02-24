package com.tomtom.codechallenge.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tomtom.codechallenge.data.DataRepository;

public class DocumentDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final DataRepository repository;
    private final String documentId;

    public DocumentDetailViewModelFactory(String documentId, DataRepository repository) {
        this.documentId = documentId;
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DocumentDetailViewModel(documentId, repository);
    }
}
