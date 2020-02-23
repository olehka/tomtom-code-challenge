package com.tomtom.codechallenge.data.network;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tomtom.codechallenge.data.Document;

import java.util.List;

class JsonDocumentDataHolder {

    @JsonProperty("docs")
    List<Document> documents;
}
