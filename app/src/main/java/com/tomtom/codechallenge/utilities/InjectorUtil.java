package com.tomtom.codechallenge.utilities;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.tomtom.codechallenge.data.DataRepository;
import com.tomtom.codechallenge.data.db.AppDatabase;
import com.tomtom.codechallenge.data.network.ApiService;
import com.tomtom.codechallenge.data.network.ApiServiceImpl;
import com.tomtom.codechallenge.data.network.HttpClient;
import com.tomtom.codechallenge.viewmodels.DocumentDetailViewModelFactory;
import com.tomtom.codechallenge.viewmodels.DocumentListViewModelFactory;

public class InjectorUtil {

    public static ApiService getApiService() {
        return ApiServiceImpl.getInstance(HttpClient.getInstance());
    }

    public static DataRepository getRepository(Context context) {
        return DataRepository.getInstance(AppDatabase.getInstance(context).documentDao());
    }

    public static DocumentListViewModelFactory getDocumentListViewModelFactory(Fragment fragment) {
        return new DocumentListViewModelFactory(getRepository(fragment.requireContext()), fragment, null);
    }

    public static DocumentDetailViewModelFactory getDocumentDetailViewModelFactory(Fragment fragment, String documentId) {
        return new DocumentDetailViewModelFactory(getRepository(fragment.requireContext()), documentId);
    }
}
