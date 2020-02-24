package com.tomtom.codechallenge.viewmodels;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.tomtom.codechallenge.data.DataRepository;
import com.tomtom.codechallenge.data.Document;

import java.util.List;

public class DocumentListViewModel extends ViewModel {

    private static final String QUERY_KEY = "QUERY";

    private final DataRepository repository;
    private final SavedStateHandle savedStateHandle;

    private final LiveData<List<Document>> documents;

    DocumentListViewModel(@NonNull DataRepository repository, @NonNull SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;
        this.documents = Transformations.switchMap(
                savedStateHandle.getLiveData(QUERY_KEY),
                (Function<String, LiveData<List<Document>>>) query -> {
                    if (TextUtils.isEmpty(query)) {
                        return repository.getAllDocumentsFromDb();
                    } else {
                        return repository.searchDocumentsByQuery(query);
                    }
                }
        );
    }

    public LiveData<List<Document>> getDocuments() {
        return documents;
    }

    public void setQuery(String query) {
        savedStateHandle.set(QUERY_KEY, query);
        repository.fetchDocumentsByQueryFromNetwork(query);
    }
}
