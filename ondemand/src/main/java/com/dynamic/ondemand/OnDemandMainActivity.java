package com.dynamic.ondemand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.play.core.splitcompat.SplitCompat;

public class OnDemandMainActivity extends AppCompatActivity {

    private Button playBtn;
    private  Button play_Btn_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_demand_main);

        initComponent();
        setListener();
    }

    private void initComponent() {

        playBtn = findViewById(R.id.playBtn);
        play_Btn_new = findViewById(R.id.play_Btn_new);
    }

    private void setListener() {

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(OnDemandMainActivity.this, VideoViewActivity.class));
            }
        });
        play_Btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(OnDemandMainActivity.this, RegisterActivity.class));
            }
        });
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        SplitCompat.install(this);
    }
}