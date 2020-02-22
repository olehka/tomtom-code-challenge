package com.tomtom.codechallenge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tomtom.codechallenge.adapters.DocumentListAdapter;
import com.tomtom.codechallenge.databinding.FragmentDocumentListBinding;

public class DocumentListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentDocumentListBinding binding = FragmentDocumentListBinding.inflate(inflater, container, false);
        DocumentListAdapter adapter = new DocumentListAdapter();
        binding.documentList.setAdapter(adapter);
        return binding.getRoot();
    }
}
