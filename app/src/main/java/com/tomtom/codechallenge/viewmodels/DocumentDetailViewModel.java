package com.tomtom.codechallenge.viewmodels;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tomtom.codechallenge.concurrent.BitmapLoadingTask;
import com.tomtom.codechallenge.concurrent.TaskRunner;
import com.tomtom.codechallenge.data.DataRepository;
import com.tomtom.codechallenge.data.Document;

import java.util.ArrayList;
import java.util.List;

public class DocumentDetailViewModel extends ViewModel {

    public static final int ISBN_MAX_SIZE = 5;

    private final LiveData<Document> document;
    private final DataRepository repository;

    private final TaskRunner taskRunner;
    private final List<MutableLiveData<Bitmap>> bitmapList;

    public DocumentDetailViewModel(String documentId, DataRepository repository, TaskRunner taskRunner) {
        this.repository = repository;
        this.document = repository.getDocumentById(documentId);
        this.taskRunner = taskRunner;
        this.bitmapList = new ArrayList<>();
        initBitmapList();
    }

    private void initBitmapList() {
        for (int i = 0; i < ISBN_MAX_SIZE; i++) {
            bitmapList.add(new MutableLiveData<>());
        }
    }

    public LiveData<Document> getDocument() {
        return document;
    }

    public List<MutableLiveData<Bitmap>> getBitmapList() {
        return bitmapList;
    }

    public void loadIsbnImage(int id, String isbnValue, String size) {
        taskRunner.executeAsync(
                new BitmapLoadingTask(repository, isbnValue, size),
                (bitmap) -> bitmapList.get(id).setValue(bitmap));
    }
}
