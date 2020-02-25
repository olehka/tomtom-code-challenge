package com.tomtom.codechallenge.data.network;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tomtom.codechallenge.data.Document;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public
class DocumentDataHolder {

    @JsonProperty("docs")
    List<Document> documents;

    public List<Document> getDocuments() {
        return documents;
    }
}
