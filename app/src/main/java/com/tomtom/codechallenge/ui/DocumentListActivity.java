package com.tomtom.codechallenge.ui;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tomtom.codechallenge.R;


public class DocumentListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_document_list);
    }
}
