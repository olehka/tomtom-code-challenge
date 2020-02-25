package com.tomtom.codechallenge.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.tomtom.codechallenge.R;
import com.tomtom.codechallenge.adapters.DocumentListAdapter;
import com.tomtom.codechallenge.data.Document;
import com.tomtom.codechallenge.data.FetchResult;
import com.tomtom.codechallenge.databinding.FragmentDocumentListBinding;
import com.tomtom.codechallenge.utilities.InjectorUtil;
import com.tomtom.codechallenge.viewmodels.DocumentListViewModel;

import java.util.Collections;
import java.util.List;

public class DocumentListFragment extends Fragment {

    private DocumentListViewModel viewModel;
    private DocumentListAdapter adapter;
    private FragmentDocumentListBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDocumentListBinding.inflate(inflater, container, false);
        adapter = new DocumentListAdapter();
        binding.documentList.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, InjectorUtil.getDocumentListViewModelFactory(this)).get(DocumentListViewModel.class);
        setSearchClickListener();
        checkArguments();
        subscribeDocuments(viewModel.getDocuments());
        subscribeErrors(viewModel.getResult());
    }

    @Override
    public void onDestroyView() {
        binding = null;
        adapter = null;
        super.onDestroyView();
    }

    private void subscribeDocuments(LiveData<List<Document>> liveData) {
        liveData.observe(getViewLifecycleOwner(), documents -> {
            hideProgressBar();
            if (documents == null || documents.isEmpty()) {
                adapter.submitList(Collections.emptyList());
            } else {
                adapter.submitList(documents);
            }
        });
    }

    private void subscribeErrors(LiveData<FetchResult> liveData) {
        liveData.observe(
                getViewLifecycleOwner(),
                result -> {
                    if (result.hasError()) {
                        showError(result.getErrorMessage());
                        hideProgressBar();
                    }
                });
    }

    private void setSearchClickListener() {
        binding.searchButton.setOnClickListener(v -> {
            Editable query = binding.searchEditText.getText();
            if (TextUtils.isEmpty(query)) {
                adapter.submitList(Collections.emptyList());
            } else {
                viewModel.searchByQuery(query.toString());
                showProgressBar();
            }
        });
    }

    private void checkArguments() {
        if (getArguments() != null) {
            String title = DocumentListFragmentArgs.fromBundle(getArguments()).getTitle();
            String author = DocumentListFragmentArgs.fromBundle(getArguments()).getAuthor();
            if (!TextUtils.isEmpty(title)) {
                binding.searchEditText.setText(title);
                viewModel.searchByTitle(title);
                showProgressBar();
            } else if (!TextUtils.isEmpty(author)) {
                binding.searchEditText.setText(author);
                viewModel.searchByAuthor(author);
                showProgressBar();
            }
        }
    }

    private void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
    }

    private void showError(String message) {
        Toast.makeText(
                getContext(),
                getString(R.string.loading_error_format, message),
                Toast.LENGTH_LONG).show();
    }
}
