package com.tomtom.codechallenge.adapters;

import android.view.View;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.tomtom.codechallenge.ui.DocumentListFragmentDirections;
import com.tomtom.codechallenge.data.Document;
import com.tomtom.codechallenge.databinding.ListItemDocumentBinding;

class DocumentListItemViewHolder extends RecyclerView.ViewHolder {

    private ListItemDocumentBinding binding;

    DocumentListItemViewHolder(ListItemDocumentBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        initClickListener();
    }

    private void initClickListener() {
        binding.setClickListener(v -> navigateToDocumentScreen(v, binding.getDocument()));
    }

    private void navigateToDocumentScreen(View view, Document document) {
        NavDirections directions = DocumentListFragmentDirections.actionDocumentListToDocumentDetailFragment(document.getId());
        Navigation.findNavController(view).navigate(directions);
    }

    void bind(Document document) {
        binding.setDocument(document);
        binding.executePendingBindings();
    }
}
