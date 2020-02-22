package com.tomtom.codechallenge.data;

import androidx.annotation.Nullable;

public class Document {

    private String id;
    private String title;
    private String author;

    public Document(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
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
        return id.equals(doc.id) && title.equals(doc.title) && author.equals(doc.author);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + id.hashCode();
        hash = 31 * hash + title.hashCode();
        hash = 31 * hash + author.hashCode();
        return hash;
    }
}