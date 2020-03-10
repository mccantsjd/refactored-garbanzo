package com.suas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

public class MissionControlActivity extends AppCompatActivity {

    private Navigator nav;
    private Handler uiHandler;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_control);
        uiHandler = new Handler(Looper.getMainLooper());
        statusText = (TextView)findViewById(R.id.statusText);
        nav = new Navigator();
    }

    private void setStatusText(String text) {
        uiHandler.post(() -> statusText.setText(text));
    }
    private void setStatusText(String text, long duration) {
        uiHandler.post(() -> statusText.setText(text));
        uiHandler.postDelayed(() -> statusText.setText(""), duration);
    }

    public void startMission(View v) {
        nav.begin();
        setStatusText("Executing mission");
    }

    public void emergencyLanding(View v) {
        try {
            nav.autoLand();
            setStatusText("Landing");
        } catch (Exception e) {
            // TODO: The underlying raw message probably shouldn't be user-facing
            setStatusText("Emergency Landing failed - Try again.\nReason: " + e.getMessage(), 10000);
        }
    }
}
