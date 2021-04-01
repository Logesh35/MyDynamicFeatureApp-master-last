package com.dynamic.mydynamicfeatureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.splitinstall.SplitInstallSessionState;
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener;
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus;

public class MainActivity extends AppCompatActivity {

    private SplitInstallManager splitInstallManager;
    private static final String DYNAMIC_MODULE_NAME = "ondemand";
    private Button downloadModuleBtn;
    private Button goNextModuleBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();
        setListener();


    }

    private void initComponent() {

        splitInstallManager = SplitInstallManagerFactory.create(this);
         downloadModuleBtn = findViewById(R.id.downloadNextModuleBtn);
        goNextModuleBtn = findViewById(R.id.goToNextModuleBtn);
        Button goTosecondModuleBtn = findViewById(R.id.goTosecondModuleBtn);

        goTosecondModuleBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                //Create the intent to start another activity
                Intent intent = new Intent(view.getContext(), NormalActivity.class);
                startActivity(intent);
            }
        });

    }



    private void updateOnDemandBtnStatus() {

        goNextModuleBtn.setEnabled(splitInstallManager.getInstalledModules().contains(DYNAMIC_MODULE_NAME));

    }


    private void setListener() {


        downloadModuleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SplitInstallRequest request = SplitInstallRequest.newBuilder().addModule(DYNAMIC_MODULE_NAME).build();
                splitInstallManager.registerListener(new SplitInstallStateUpdatedListener() {
                    @Override
                    public void onStateUpdate(SplitInstallSessionState state) {

                        switch (state.status()) {

                            case SplitInstallSessionStatus.DOWNLOADING:
                                Toast.makeText(MainActivity.this, "Downloading", Toast.LENGTH_SHORT).show();
                                break;

                            case SplitInstallSessionStatus.CANCELING:
                                Toast.makeText(MainActivity.this, "Cancelling", Toast.LENGTH_SHORT).show();
                                break;
                            case SplitInstallSessionStatus.CANCELED:
                                Toast.makeText(MainActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                                break;

                            case SplitInstallSessionStatus.FAILED:
                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                break;

                            case SplitInstallSessionStatus.DOWNLOADED:
                                Toast.makeText(MainActivity.this, "Downloaded", Toast.LENGTH_SHORT).show();
                                break;

                            case SplitInstallSessionStatus.INSTALLING:
                                Toast.makeText(MainActivity.this, "Installing", Toast.LENGTH_SHORT).show();
                                break;

                            case SplitInstallSessionStatus.PENDING:
                                Toast.makeText(MainActivity.this, "Pending", Toast.LENGTH_SHORT).show();
                                break;

                            case SplitInstallSessionStatus.INSTALLED:
                                Toast.makeText(MainActivity.this, "Installed", Toast.LENGTH_SHORT).show();
                                updateOnDemandBtnStatus();
                                break;

                        }

                    }
                });
                splitInstallManager.startInstall(request);

            }

        });

        goNextModuleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName(BuildConfig.APPLICATION_ID, "com.dynamic.ondemand.OnDemandMainActivity");
                startActivity(intent);
            }
        });
    }



    }

