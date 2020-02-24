package com.tomtom.codechallenge.concurrent;

import android.util.Log;

import java.util.concurrent.Callable;

public class TaskRunner {

    private final AppExecutors appExecutors;

    public TaskRunner(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
    }

    public interface Callback<R> {
        void onComplete(R result);
    }

    public <R> void executeAsync(Callable<R> callable, Callback<R> callback) {
        appExecutors.networkIO().execute(() -> {
            try {
                final R result = callable.call();
                appExecutors.mainThread().execute(() -> callback.onComplete(result));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("TaskRunner", "Error: " + e.getMessage());
            }
        });
    }
}