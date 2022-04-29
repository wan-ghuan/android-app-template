package com.wangxb.app.template;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;

import com.wangxb.component.executor.WorkExecutor;
import com.wangxb.component.log.Log;

public final class App extends Application {
    private static final String TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: 1111111");

        WorkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "entry");
                View view =  LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_splash, null);
                Log.i(TAG, "leave");
            }
        });
    }
}
