package com.tomtom.codechallenge.utilities;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.tomtom.codechallenge.concurrent.AppExecutors;
import com.tomtom.codechallenge.concurrent.TaskRunner;
import com.tomtom.codechallenge.data.DataRepository;
import com.tomtom.codechallenge.data.db.AppDatabase;
import com.tomtom.codechallenge.data.db.DocumentDao;
import com.tomtom.codechallenge.data.network.ApiService;
import com.tomtom.codechallenge.data.network.ApiServiceImpl;
import com.tomtom.codechallenge.data.network.HttpClientImpl;
import com.tomtom.codechallenge.viewmodels.DocumentDetailViewModelFactory;
import com.tomtom.codechallenge.viewmodels.DocumentListViewModelFactory;

public class InjectorUtil {

    public static AppExecutors getAppExecutors() {
        return AppExecutors.getInstance();
    }

    public static ApiService getApiService() {
        return ApiServiceImpl.getInstance(HttpClientImpl.getInstance());
    }

    public static DocumentDao getDocumentDao(Context context) {
        return AppDatabase.getInstance(context).documentDao();
    }

    public static DataRepository getRepository(Context context) {
        return DataRepository.getInstance(getDocumentDao(context), getApiService(), getAppExecutors());
    }

    public static DocumentListViewModelFactory getDocumentListViewModelFactory(Fragment fragment) {
        return new DocumentListViewModelFactory(getRepository(fragment.requireContext()));
    }

    public static DocumentDetailViewModelFactory getDocumentDetailViewModelFactory(Fragment fragment, String documentId) {
        return new DocumentDetailViewModelFactory(documentId, getRepository(fragment.requireContext()), new TaskRunner(getAppExecutors()));
    }
}
