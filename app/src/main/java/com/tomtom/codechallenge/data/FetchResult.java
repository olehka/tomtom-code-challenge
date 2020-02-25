package com.tomtom.codechallenge.data;

public final class FetchResult {
    private final String errorMessage;

    private FetchResult(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    static FetchResult success() {
        return new FetchResult(null);
    }

    static FetchResult error(String errorMessage) {
        return new FetchResult(errorMessage);
    }

    public Boolean hasError() {
        return errorMessage != null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}