package com.tomtom.codechallenge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tomtom.codechallenge.databinding.FragmentDocumentDetailBinding;

public class DocumentDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentDocumentDetailBinding binding = FragmentDocumentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
