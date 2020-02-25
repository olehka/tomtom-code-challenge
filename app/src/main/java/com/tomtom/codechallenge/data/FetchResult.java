package com.tomtom.codechallenge.data;

public final class FetchResult {
    private final String errorMessage;

    private FetchResult(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static FetchResult success() {
        return new FetchResult(null);
    }

    public static FetchResult error(String errorMessage) {
        return new FetchResult(errorMessage);
    }

    public Boolean hasError() {
        return errorMessage != null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}