package com.tomtom.codechallenge.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tomtom.codechallenge.data.DataRepository;

public class DocumentDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final DataRepository repository;
    private final String documentId;

    public DocumentDetailViewModelFactory(DataRepository repository, String documentId) {
        this.repository = repository;
        this.documentId = documentId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DocumentDetailViewModel(repository, documentId);
    }
}
