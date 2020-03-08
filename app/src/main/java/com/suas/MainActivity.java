package com.suas;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import dji.common.error.DJIError;
import dji.common.error.DJISDKError;
import dji.sdk.base.BaseComponent;
import dji.sdk.base.BaseProduct;
import dji.sdk.sdkmanager.DJISDKInitEvent;
import dji.sdk.sdkmanager.DJISDKManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    public static final String FLAG_CONNECTION_CHANGE = "dji_sdk_connection_change";
    private static BaseProduct product;
    private Handler uiHandler;
    private Handler handler;

    // TODO: Remove unneeded permissions
    private static final String[] REQUIRED_PERMISSION_LIST = new String[]{
            Manifest.permission.VIBRATE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
    };
    private List<String> missingPermission = new ArrayList<>();
    private static final int REQUEST_PERMISSION_CODE = 12345;

    private ConnectivityManager cm;

    private TextView statusText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        uiHandler = new Handler(Looper.getMainLooper());

        cm = (ConnectivityManager)getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // NOTE: Android Studio will say these casts are redundant, but the app crashed without them
        statusText = (TextView)findViewById(R.id.statusText);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        // When the compile and target version is higher than 22, please request the following permission at runtime to ensure the SDK works well.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions();
        }
    }

    private void setStatusText(String text) {
        uiHandler.post(() -> statusText.setText(text));
    }

    private void setStatusText(String text, long delay) {
        uiHandler.postDelayed(() -> statusText.setText(text), delay);
    }

    /**
     * Checks if there is any missing permissions, and
     * requests runtime permission if needed.
     */
    private void checkAndRequestPermissions() {
        // Check for permissions
        for (String eachPermission : REQUIRED_PERMISSION_LIST) {
            if (ContextCompat.checkSelfPermission(this, eachPermission) != PackageManager.PERMISSION_GRANTED) {
                missingPermission.add(eachPermission);
            }
        }
        // Request for missing permissions
        if (missingPermission.isEmpty()) {
            startSDKRegistration();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setStatusText("Requesting permissions...");
            ActivityCompat.requestPermissions(this,
                    missingPermission.toArray(new String[missingPermission.size()]),
                    REQUEST_PERMISSION_CODE);
        }
    }

    /**
     * Result of runtime permission request
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Check for granted permission and remove from missing list
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = grantResults.length - 1; i >= 0; i--) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    missingPermission.remove(permissions[i]);
                }
            }
        }
        // If there is enough permission, we will start the registration
        if (missingPermission.isEmpty()) {
            startSDKRegistration();
        } else {
            setStatusText("Error: Missing permissions");
        }
    }

    public void startSDKRegistration() {
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
        if (!isConnected) {
            setStatusText("Registration failed: No internet connection detected. \nRetrying...");
            uiHandler.postDelayed(() -> startSDKRegistration(), 5000);
        }
        else {
            setStatusText("Registering application...");
            DJISDKManager.getInstance().registerApp(MainActivity.this.getApplicationContext(), new DJISDKManager.SDKManagerCallback() {
                @Override
                public void onRegister(DJIError djiError) {
                    if (djiError == DJISDKError.REGISTRATION_SUCCESS) {
                        setStatusText("Waiting for connection to drone WiFi...");
                        DJISDKManager.getInstance().startConnectionToProduct();
                    } else {
                        setStatusText("Registration failed: " + djiError.getDescription());
                    }
                    Log.v(TAG, djiError.getDescription());
                }

                @Override
                public void onProductDisconnect() {
                    Log.d(TAG, "onProductDisconnect");
                    notifyStatusChange();
                }

                @Override
                public void onProductConnect(BaseProduct baseProduct) {
                    Log.d(TAG, String.format("onProductConnect newProduct:%s", baseProduct));
                    setStatusText("Drone connection successful");
                    uiHandler.post(() -> progressBar.setVisibility(View.GONE));
                    notifyStatusChange();
                }

                @Override
                public void onComponentChange(BaseProduct.ComponentKey componentKey, BaseComponent oldComponent,
                                              BaseComponent newComponent) {

                    if (newComponent != null) {
                        newComponent.setComponentListener(new BaseComponent.ComponentListener() {

                            @Override
                            public void onConnectivityChange(boolean isConnected) {
                                Log.d(TAG, "onComponentConnectivityChanged: " + isConnected);
                                notifyStatusChange();
                            }
                        });
                    }
                    Log.d(TAG,
                            String.format("onComponentChange key:%s, oldComponent:%s, newComponent:%s",
                                    componentKey,
                                    oldComponent,
                                    newComponent));

                }

                @Override
                public void onInitProcess(DJISDKInitEvent djisdkInitEvent, int i) {

                }

                @Override
                public void onDatabaseDownloadProgress(long l, long l1) {

                }
            });
        }
    }

    private void notifyStatusChange() {
        uiHandler.removeCallbacks(updateRunnable);
        uiHandler.postDelayed(updateRunnable, 500);
    }

    private Runnable updateRunnable = new Runnable() {

        @Override
        public void run() {
            Intent intent = new Intent(FLAG_CONNECTION_CHANGE);
            sendBroadcast(intent);
        }
    };
}
