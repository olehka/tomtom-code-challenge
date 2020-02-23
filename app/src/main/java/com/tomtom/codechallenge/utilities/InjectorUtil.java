package com.tomtom.codechallenge.utilities;

import androidx.fragment.app.Fragment;

import com.tomtom.codechallenge.data.DataRepository;
import com.tomtom.codechallenge.viewmodels.DocumentDetailViewModelFactory;
import com.tomtom.codechallenge.viewmodels.DocumentListViewModelFactory;

public class InjectorUtil {

    public static DataRepository getRepository() {
        return DataRepository.getInstance();
    }

    public static DocumentListViewModelFactory getDocumentListViewModelFactory(Fragment fragment) {
        return new DocumentListViewModelFactory(getRepository(), fragment, null);
    }

    public static DocumentDetailViewModelFactory getDocumentDetailViewModelFactory(Fragment fragment, String documentId) {
        return new DocumentDetailViewModelFactory(getRepository(), documentId);
    }
}
