package com.tomtom.codechallenge.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.tomtom.codechallenge.data.Document;

public class DocumentDiffCallback extends DiffUtil.ItemCallback<Document> {

    @Override
    public boolean areItemsTheSame(@NonNull Document oldItem, @NonNull Document newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Document oldItem, @NonNull Document newItem) {
        return oldItem.equals(newItem);
    }
}
