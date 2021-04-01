package com.dynamic.ondemand;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class VideoViewActivity extends AppCompatActivity {

    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        initComponent();
    }

    private void initComponent() {

        pdfView = findViewById(R.id.pdfView);

        pdfView.fromAsset("book.pdf").load();
    }

}