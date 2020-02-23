package com.tomtom.codechallenge.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tomtom.codechallenge.data.Document;

import java.util.List;

@Dao
public interface DocumentDao {

    @Query("SELECT * FROM documents")
    LiveData<List<Document>> loadDocuments();

    @Query("SELECT * FROM documents WHERE title LIKE :query OR author LIKE :query")
    LiveData<List<Document>> searchDocuments(String query);

    @Query("SELECT * FROM documents WHERE id = :documentId")
    LiveData<Document> loadDocument(String documentId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveDocuments(List<Document> documents);
}
