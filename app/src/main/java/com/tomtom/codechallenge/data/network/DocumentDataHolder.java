package com.tomtom.codechallenge.data.network;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tomtom.codechallenge.data.Document;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class DocumentDataHolder {

    @JsonProperty("docs")
    List<Document> documents;
}
