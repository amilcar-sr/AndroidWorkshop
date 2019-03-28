package com.codesgood.androidworkshop.ui.activity;

import android.os.Bundle;


import com.codesgood.androidworkshop.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    //TODO: [45] Add a method that will provide this class with the activity's instance layout id
    public abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        toolbar = findViewById(R.id.toolbar);

        //TODO: [44] Assign the custom toolbar to this activity
        setSupportActionBar(toolbar);
    }
}
