package com.tomtom.codechallenge.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.tomtom.codechallenge.data.Document;
import com.tomtom.codechallenge.databinding.ListItemDocumentBinding;

public class DocumentListAdapter extends ListAdapter<Document, RecyclerView.ViewHolder> {

    public DocumentListAdapter() {
        super(new DocumentDiffCallback());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DocumentListItemViewHolder(ListItemDocumentBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Document document = getItem(position);
        ((DocumentListItemViewHolder)holder).bind(document);
    }
}