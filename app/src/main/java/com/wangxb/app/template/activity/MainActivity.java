package com.wangxb.app.template.activity;

import com.wangxb.app.template.R;
import com.wangxb.app.template.ui.main.MainFragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.container, MainFragment.newInstance())
                                       .commitNow();
        }
    }
}
