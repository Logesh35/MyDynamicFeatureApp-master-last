package com.dynamic.newdynamicfeature;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.play.core.splitcompat.SplitCompat;

public class NewOnDemandMainActivity extends AppCompatActivity {

    private Button newplayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newon_demand_main);

        initComponent();
        setListener();
    }

    private void initComponent() {

        newplayBtn = findViewById(R.id.newplayBtn);

    }

    private void setListener() {

        newplayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(NewOnDemandMainActivity.this, LoginActivity.class));
            }
        });

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        SplitCompat.install(this);
    }
}