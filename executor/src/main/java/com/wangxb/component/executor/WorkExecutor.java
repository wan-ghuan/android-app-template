
package com.wangxb.component.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

public final class WorkExecutor {
    private static final ExecutorService mWorkExecutor;

    static {
        int threadCount = Runtime.getRuntime().availableProcessors() * 2;
        mWorkExecutor = Executors.newFixedThreadPool(threadCount, runnable -> {
            Thread thread = new Thread(runnable, "Work Thread");
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.setDaemon(true);
            return thread;
        });
    }

    public static void execute(@NonNull Runnable command) {
        mWorkExecutor.execute(command);
    }
}
