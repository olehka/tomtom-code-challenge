package com.tomtom.codechallenge.util;

import com.tomtom.codechallenge.concurrent.AppExecutors;

import java.util.concurrent.Executor;

public class InstantAppExecutors extends AppExecutors {

    private static  final Executor executor = Runnable::run;

    public InstantAppExecutors() {
        super(executor, executor, executor);
    }
}
