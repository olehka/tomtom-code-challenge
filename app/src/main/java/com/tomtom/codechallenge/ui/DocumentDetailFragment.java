package com.tomtom.codechallenge.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.tomtom.codechallenge.data.Document;
import com.tomtom.codechallenge.databinding.FragmentDocumentDetailBinding;
import com.tomtom.codechallenge.utilities.InjectorUtil;
import com.tomtom.codechallenge.viewmodels.DocumentDetailViewModel;

public class DocumentDetailFragment extends Fragment {

    private FragmentDocumentDetailBinding binding;
    private DocumentDetailViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDocumentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String documentId = DocumentDetailFragmentArgs.fromBundle(getArguments()).getDocumentId();
        viewModel = new ViewModelProvider(this, InjectorUtil.getDocumentDetailViewModelFactory(this, documentId)).get(DocumentDetailViewModel.class);
        setClickListeners();
        subscribeUi(viewModel.getDocument());
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    private void subscribeUi(LiveData<Document> liveData) {
        liveData.observe(getViewLifecycleOwner(), document -> {
            if (document != null) {
                binding.documentTitle.setText(document.getTitle());
                binding.documentAuthor.setText(document.getAuthor());
            }
        });
    }

    private void setClickListeners() {
        //TODO add title, author handler
    }
}
