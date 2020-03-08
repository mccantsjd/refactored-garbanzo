package com.suas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MissionControlActivity extends AppCompatActivity {

    private Navigator nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_control);

        nav = new Navigator();
    }

    public void startMission(View v) {
        nav.begin();
    }

    public void emergencyLanding(View v) {
        nav.autoLand();
    }
}
