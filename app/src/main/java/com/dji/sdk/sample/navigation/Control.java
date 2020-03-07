package com.dji.sdk.sample.navigation;

import dji.sdk.mission.timeline.TimelineEvent;
import dji.sdk.mission.timeline.actions.AircraftYawAction;
import dji.sdk.mission.timeline.actions.LandAction;
import dji.sdk.mission.timeline.actions.TakeOffAction;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import com.dji.sdk.sample.internal.controller.DJISampleApplication;

import java.util.ArrayList;

import dji.common.error.DJIError;
import dji.sdk.mission.MissionControl;
import dji.sdk.mission.timeline.TimelineElement;
import dji.sdk.mission.timeline.actions.TakeOffAction;
import dji.sdk.mission.timeline.triggers.Trigger;
import dji.sdk.mobilerc.MobileRemoteController;
import dji.sdk.sdkmanager.DJISDKManager;

public class Control {
    private MissionControl mc;
    public Control(){
        mc = DJISDKManager.getInstance().getMissionControl();
        mc.addListener(new MissionControl.Listener() {
            @Override
            public void onEvent(@Nullable TimelineElement timelineElement, TimelineEvent timelineEvent, @Nullable DJIError djiError) {
                String element = (timelineElement != null) ? timelineElement.getClass().getSimpleName() : "null";
                Log.d("suas.timeline", String.format("Element: %s - Event: %s", element, timelineEvent.name()));
            }
        });
    }
    public void takeOff(){
        mc.unscheduleEverything();
        mc.scheduleElement(new TakeOffAction());
        mc.startTimeline();

    }
    public void forward(){
        mc.scheduleElement(new FlyForwardElement(5));
    }
    public void land(){
        mc.scheduleElement(new LandAction());
    }
    public void turnRight(){
        mc.scheduleElement(new AircraftYawAction(90.0f, false));
    }
    public void turnLeft(){
        mc.scheduleElement(new AircraftYawAction(-90.0f, false));
    }
    public void pause(){
        mc.scheduleElement(new AircraftYawAction(0, false));
    }
}
