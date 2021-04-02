package com.dynamic.mydynamicfeatureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.splitinstall.SplitInstallSessionState;
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener;
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus;
import com.google.android.play.core.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {
    AppUpdateManager appUpdateManager;
    int RequestUpdate = 1;
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


        appUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if((result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE)
                        && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE))
                {
                    try {
                        appUpdateManager.startUpdateFlowForResult(
                                result,
                                AppUpdateType.IMMEDIATE,
                                MainActivity.this,
                                RequestUpdate);
                    }
                    catch (IntentSender.SendIntentException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if(result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS)
                {
                    try {
                        appUpdateManager.startUpdateFlowForResult(
                                result,
                                AppUpdateType.IMMEDIATE,
                               MainActivity.this,
                                RequestUpdate);
                    }
                    catch (IntentSender.SendIntentException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }






    private void updateOnDemandBtnStatus() {

        goNextModuleBtn.setEnabled(splitInstallManager.getInstalledModules().contains(DYNAMIC_MODULE_NAME));

    }


    private void setListener() {


        downloadModuleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view)
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
                                goNextModuleBtn.setVisibility(View.VISIBLE);
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

