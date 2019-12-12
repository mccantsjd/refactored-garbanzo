package com.dji.sdk.sample.navigation;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import com.dji.sdk.sample.internal.controller.DJISampleApplication;

import java.util.ArrayList;

import dji.common.error.DJIError;
import dji.sdk.mission.MissionControl;
import dji.sdk.mission.timeline.TimelineElement;
import dji.sdk.mission.timeline.triggers.Trigger;
import dji.sdk.mobilerc.MobileRemoteController;
import dji.sdk.sdkmanager.DJISDKManager;


public class FlyForwardElement extends TimelineElement {

    private MissionControl mc;
    private MobileRemoteController mrc;
    private int stopAfter;

    public FlyForwardElement() {
        this(0);
    }

    public FlyForwardElement(int stopAfter) {
        this.mc = DJISDKManager.getInstance().getMissionControl();
        this.mrc = DJISampleApplication.getAircraftInstance().getMobileRemoteController();
        this.stopAfter = stopAfter;

        // Stop and finish this action on object detection
        ObjectDetectionTrigger trigger = new ObjectDetectionTrigger(new Trigger.Action() {
            @Override
            public void onCall() { stop(); }
        });
        ArrayList<Trigger> triggers = new ArrayList<>();
        triggers.add(trigger);
        setTriggers(triggers);
    }

    @Override
    public void run() {
        mc.onStart(this);
        mrc.setRightStickVertical(0.3f);

        if (stopAfter > 0) {
            SystemClock.sleep(stopAfter * 1000);
            Log.d("suas.element", "FlyForwardElement stopAfter");
            FlyForwardElement.this.stop();
        }
    }

    @Override
    public void stop() {
        mrc.setRightStickVertical(0.0f);
        mc.onFinishWithError(this, null);
    }

    @Override
    public void finishRun(@Nullable DJIError djiError) { }

    @Override
    public DJIError checkValidity() {
        return null;
    }

    @Override
    public boolean isPausable() {
        return false;
    }
}
