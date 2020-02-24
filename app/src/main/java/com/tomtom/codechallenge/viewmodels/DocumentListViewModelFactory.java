package com.tomtom.codechallenge.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tomtom.codechallenge.data.DataRepository;

public class DocumentListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final DataRepository repository;

    public DocumentListViewModelFactory(@NonNull DataRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DocumentListViewModel(repository);
    }
}