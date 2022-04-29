
package com.wangxb.component.executor;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

public final class MainExecutor  {
    private static final Handler mMainHandler;

    static  {
        mMainHandler = new Handler(Looper.getMainLooper());
    }

    public static void execute(@NonNull Runnable command) {
        mMainHandler.post(command);
    }

    @SuppressWarnings("unused")
    public static void executeDelay(@NonNull Runnable command, long delayMillis) {
        mMainHandler.postDelayed(command, delayMillis);
    }
}
