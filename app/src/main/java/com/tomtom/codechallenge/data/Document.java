package com.tomtom.codechallenge.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(tableName = "documents")
public class Document {

    @NonNull
    @PrimaryKey
    @JsonProperty("key")
    private String id;

    @JsonProperty
    private String title;

    @JsonProperty("author_name")
    private List<String> authors;

    public Document() {}

    public Document(@NonNull String id, String title, List<String> authors) {
        this.id = id;
        this.title = title;
        this.authors = authors;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getAuthor() {
        return authors.isEmpty() ? "" : authors.get(0);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Document)) {
            return false;
        }
        Document doc = (Document) obj;
        return id.equals(doc.id) && title.equals(doc.title) && authors.equals(doc.authors);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + id.hashCode();
        hash = 31 * hash + title.hashCode();
        hash = 31 * hash + authors.hashCode();
        return hash;
    }
}