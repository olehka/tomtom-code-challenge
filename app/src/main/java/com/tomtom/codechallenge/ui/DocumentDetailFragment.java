package com.tomtom.codechallenge.ui;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.tomtom.codechallenge.R;
import com.tomtom.codechallenge.data.Document;
import com.tomtom.codechallenge.data.network.ApiService;
import com.tomtom.codechallenge.databinding.FragmentDocumentDetailBinding;
import com.tomtom.codechallenge.utilities.InjectorUtil;
import com.tomtom.codechallenge.viewmodels.DocumentDetailViewModel;

import java.util.List;

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
        if (getArguments() == null) {
            return;
        }
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
                createIsbnList(document.getIsbnList());
            }
        });
    }

    private void createIsbnList(List<String> isbnList) {
        if (isbnList == null || isbnList.isEmpty()) {
            return;
        }
        binding.isbnLayout.removeAllViews();
        int size = Math.min(isbnList.size(), DocumentDetailViewModel.ISBN_MAX_SIZE);
        for (int i = 0; i < size; i++) {
            String isbn = isbnList.get(i);
            viewModel.loadIsbnImage(i, isbn, ApiService.IMAGE_SIZE_MEDIUM);
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setId(i);
            textView.setTag(getString(R.string.isbn_tag) + i);
            textView.setText(isbn);
            binding.isbnLayout.addView(textView);
            subscribeIsbnImage(i, textView);
        }
    }

    private void subscribeIsbnImage(int id, TextView textView) {
        viewModel.getBitmapList().get(id).observe(getViewLifecycleOwner(), bitmap -> {
            if (bitmap != null) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, bitmapDrawable);
            }
        });
    }

    private void setClickListeners() {
        binding.documentTitle.setOnClickListener(v -> navigateToDocumentListScreen(
                v, binding.documentTitle.getText().toString(), ""
        ));
        binding.documentAuthor.setOnClickListener(v -> navigateToDocumentListScreen(
                v, "", binding.documentAuthor.getText().toString()
        ));
    }

    private void navigateToDocumentListScreen(View view, String title, String author) {
        NavDirections directions = DocumentDetailFragmentDirections
                .actionDocumentDetailToDocumentListFragmane()
                .setTitle(title)
                .setAuthor(author);
        Navigation.findNavController(view).navigate(directions);
    }
}
