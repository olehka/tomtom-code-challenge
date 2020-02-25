package com.tomtom.codechallenge.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
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

    @JsonProperty("isbn")
    private List<String> isbnList;

    public Document() {}

    @Ignore
    public Document(@NonNull String id, String title, List<String> authors, List<String> isbnList) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.isbnList = isbnList;
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

    public void setIsbnList(List<String> isbnList) {
        this.isbnList = isbnList;
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
        return (authors == null || authors.isEmpty()) ? "" : authors.get(0);
    }

    public List<String> getIsbnList() {
        return isbnList;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Document)) {
            return false;
        }
        Document doc = (Document) obj;
        return id.equals(doc.id) && title.equals(doc.title) && getAuthor().equals(doc.getAuthor());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + id.hashCode();
        hash = 31 * hash + title.hashCode();
        hash = 31 * hash + getAuthor().hashCode();
        return hash;
    }
}