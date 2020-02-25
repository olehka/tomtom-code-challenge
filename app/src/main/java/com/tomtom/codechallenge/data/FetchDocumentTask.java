package com.tomtom.codechallenge.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tomtom.codechallenge.data.db.DocumentDao;
import com.tomtom.codechallenge.data.network.ApiResponse;

import java.util.List;

public abstract class FetchDocumentTask implements Runnable {

    private final DocumentDao documentDao;
    private final MutableLiveData<FetchResult> resultLiveData;

    FetchDocumentTask(DocumentDao documentDao) {
        this.documentDao = documentDao;
        this.resultLiveData = new MutableLiveData<>();
    }

    @Override
    public void run() {
        ApiResponse<List<Document>> response = fetchDocuments();
        if (response.hasError()) {
            Log.e("DataRepository", "Network error: " + response.getError());
            resultLiveData.postValue(FetchResult.error("Network error: " + response.getError()));
        } else {
            List<Document> documentList = response.getBody();
            if (documentList == null || documentList.isEmpty()) {
                Log.d("DataRepository", "Empty document list");
                resultLiveData.postValue(FetchResult.error("Empty document list"));
            } else {
                Log.d("DataRepository", "Save documents: " + documentList.size());
                resultLiveData.postValue(FetchResult.success());
                documentDao.saveDocuments(documentList);
            }
        }
    }

    LiveData<FetchResult> getResultLiveData() {
        return resultLiveData;
    }

    abstract ApiResponse<List<Document>> fetchDocuments();
}