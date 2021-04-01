package com.dynamic.mydynamicfeatureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.splitinstall.SplitInstallSessionState;
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener;
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus;

public class NormalActivity extends AppCompatActivity {

    private SplitInstallManager splitInstallManager;
    private static final String DYNAMIC_MODULE_NAME2 = "newdynamicfeature";
    private  Button downloadAddModuleBtn;
    private  Button  goToLoginModuleBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        initComponent();

        newsetListener();

    }

    private void initComponent() {

        splitInstallManager = SplitInstallManagerFactory.create(this);
        downloadAddModuleBtn = findViewById(R.id.downloadAddModuleBtn);
        goToLoginModuleBtn = findViewById(R.id.goToLoginModuleBtn);


    }

    private void updatenewOnDemandBtnStatus() {
        goToLoginModuleBtn.setEnabled(splitInstallManager.getInstalledModules().contains(DYNAMIC_MODULE_NAME2));
        Log.d("myTag", "This is my message");
    }


    private void  newsetListener()
    {
        downloadAddModuleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SplitInstallRequest request = SplitInstallRequest.newBuilder().addModule(DYNAMIC_MODULE_NAME2).build();
                splitInstallManager.registerListener(new SplitInstallStateUpdatedListener() {
                    @Override
                    public void onStateUpdate(SplitInstallSessionState state) {

                        switch (state.status()) {

                            case SplitInstallSessionStatus.DOWNLOADING:
                                Toast.makeText(NormalActivity.this, "Downloading", Toast.LENGTH_SHORT).show();
                                break;

                            case SplitInstallSessionStatus.CANCELING:
                                Toast.makeText(NormalActivity.this, "Cancelling", Toast.LENGTH_SHORT).show();
                                break;
                            case SplitInstallSessionStatus.CANCELED:
                                Toast.makeText(NormalActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                                break;

                            case SplitInstallSessionStatus.FAILED:
                                Toast.makeText(NormalActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                break;

                            case SplitInstallSessionStatus.DOWNLOADED:
                                Toast.makeText(NormalActivity.this, "Downloaded", Toast.LENGTH_SHORT).show();
                                break;

                            case SplitInstallSessionStatus.INSTALLING:
                                Toast.makeText(NormalActivity.this, "Installing", Toast.LENGTH_SHORT).show();
                                break;

                            case SplitInstallSessionStatus.PENDING:
                                Toast.makeText(NormalActivity.this, "Pending", Toast.LENGTH_SHORT).show();
                                break;

                            case SplitInstallSessionStatus.INSTALLED:
                                Toast.makeText(NormalActivity.this, "Installed", Toast.LENGTH_SHORT).show();
                               //updatenewOnDemandBtnStatus();
                                break;

                        }

                    }
                });
                splitInstallManager.startInstall(request);

            }

        });

        goToLoginModuleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName(BuildConfig.APPLICATION_ID, "com.dynamic.newdynamicfeature.NewOnDemandMainActivity");
                startActivity(intent);
            }
        });

    }
}