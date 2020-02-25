package com.tomtom.codechallenge.viewmodels;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.tomtom.codechallenge.data.DataRepository;
import com.tomtom.codechallenge.data.Document;
import com.tomtom.codechallenge.data.FetchResult;

import java.util.List;

public class DocumentListViewModel extends ViewModel {

    private final LiveData<List<Document>> documents;
    private final LiveData<FetchResult> result;
    private final MutableLiveData<SearchPair> search;

    DocumentListViewModel(@NonNull DataRepository repository) {
        this.documents = repository.getAllDocuments();
        this.search = new MutableLiveData<>();
        this.result = Transformations.switchMap(
                search,
                input -> {
                    switch (input.type) {
                        case SearchPair.TYPE_AUTHOR:
                            return repository.fetchDocumentsByAuthor(input.value);
                        case SearchPair.TYPE_TITLE:
                            return repository.fetchDocumentsByTitle(input.value);
                        default:
                            return repository.fetchDocumentsByQuery(input.value);
                    }
                }
        );
    }

    public LiveData<List<Document>> getDocuments() {
        return documents;
    }

    public void searchByQuery(String query) {
        search.setValue(new SearchPair(SearchPair.TYPE_QUERY, query));
    }

    public void searchByTitle(String title) {
        search.setValue(new SearchPair(SearchPair.TYPE_TITLE, title));
    }

    public void searchByAuthor(String author) {
        search.setValue(new SearchPair(SearchPair.TYPE_AUTHOR, author));
    }

    public LiveData<FetchResult> getResult() {
        return result;
    }

    static class SearchPair {

        static final int TYPE_QUERY = 0;
        static final int TYPE_TITLE = 1;
        static final int TYPE_AUTHOR = 2;

        private final int type;
        private final String value;

        SearchPair(int type, String value) {
            this.type = type;
            this.value = value;
        }
    }
}
