package com.tomtom.codechallenge.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.tomtom.codechallenge.adapters.DocumentListAdapter;
import com.tomtom.codechallenge.data.Document;
import com.tomtom.codechallenge.databinding.FragmentDocumentListBinding;
import com.tomtom.codechallenge.utilities.InjectorUtil;
import com.tomtom.codechallenge.viewmodels.DocumentListViewModel;

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
        subscribeUi(viewModel.getDocuments());
    }

    @Override
    public void onDestroyView() {
        binding = null;
        adapter = null;
        super.onDestroyView();
    }

    private void subscribeUi(LiveData<List<Document>> liveData) {
        liveData.observe(getViewLifecycleOwner(), documents -> {
            if (documents != null && !documents.isEmpty()) {
                adapter.submitList(documents);
            } else {
                Log.e("DocumentListFragment", "Error: documents are empty");
            }
        });
    }

    private void setSearchClickListener() {
        binding.searchButton.setOnClickListener(v -> {
            String query = binding.searchEditText.getText().toString();
            viewModel.setQuery(query);
        });
    }
}
