package com.dji.sdk.sample.navigation;

import android.util.Log;

import androidx.annotation.Nullable;

import com.dji.sdk.sample.internal.utils.ModuleVerificationUtil;

import java.util.ArrayList;

import dji.common.error.DJIError;
import dji.common.flightcontroller.ConnectionFailSafeBehavior;
import dji.sdk.flightcontroller.FlightAssistant;
import dji.sdk.flightcontroller.FlightController;
import dji.sdk.mission.MissionControl;
import dji.sdk.mission.timeline.TimelineElement;
import dji.sdk.mission.timeline.TimelineEvent;
import dji.sdk.mission.timeline.actions.AircraftYawAction;
import dji.sdk.mission.timeline.actions.LandAction;
import dji.sdk.mission.timeline.actions.TakeOffAction;
import dji.sdk.mission.timeline.triggers.Trigger;
import dji.sdk.sdkmanager.DJISDKManager;



public class Navigator {

    private MissionControl mc;
    private FlightController fc;
    private FlightAssistant fa;
    public int n = 0;

    public Navigator() {

        // Disconnect failsafe
        fc = ModuleVerificationUtil.getFlightController();
        fc.setConnectionFailSafeBehavior(ConnectionFailSafeBehavior.LANDING,
                null);

        mc = DJISDKManager.getInstance().getMissionControl();

        // Disable automatic avoidance systems, so they don't interfere with
        // our logic
        fa = fc.getFlightAssistant();
        fa.setCollisionAvoidanceEnabled(false, null);
        fa.setActiveObstacleAvoidanceEnabled(false, null);
        fa.setAdvancedPilotAssistanceSystemEnabled(false, null);

        mc.addListener(new MissionControl.Listener() {
            @Override
            public void onEvent(@Nullable TimelineElement timelineElement, TimelineEvent timelineEvent, @Nullable DJIError djiError) {
                String element = (timelineElement != null) ? timelineElement.getClass().getSimpleName() : "null";
                Log.d("suas.timeline", String.format("Element: %s - Event: %s", element, timelineEvent.name()));
            }
        });
    }

    public void begin() {

        //mc.unscheduleEverything();
        //mc.scheduleElement(new TakeOffAction());

        Control c = new Control();
        //ObjectDetectionTrigger trigger = new ObjectDetectionTrigger(new Trigger.Action() {
          //  @Override
            //public void onCall() { n = 1; }
        //});

        c.takeOff();
        c.forward();
        c.forward();
        if(n == 1){
            c.land();
        }
        //land();
       // mc.scheduleElement(new FlyForwardElement(5));
       // mc.scheduleElement(new AircraftYawAction(-90.0f, false));
       // mc.scheduleElement(new FlyForwardElement(5));
       // mc.scheduleElement(new AircraftYawAction(0, false));
       // mc.scheduleElement(new FlyForwardElement(5));
       // mc.scheduleElement(new AircraftYawAction(0, false));
       // mc.scheduleElement(new FlyForwardElement(5));
        /*
        mc.scheduleElement(new FlyForwardElement());
        mc.scheduleElement(new AircraftYawAction(-90.0f, false));
        mc.scheduleElement(new FlyForwardElement(4));
        mc.scheduleElement(new AircraftYawAction(90.0f, false));
        mc.scheduleElement(new FlyForwardElement(6));

         */
       // mc.scheduleElement(new LandAction());
       // mc.startTimeline();
    }

    private void forceStopMissionTimeline() {
        // It seems that calling stopTimeline doesn't necessarily stop
        // the currently running element (or maybe FlyForwardElement is
        // just broken). It does reset the marker index though, so this
        // logic gets a little nuanced:
        //
        // - Get the running element
        // - Stop the timeline so that the next element doesn't run
        //   when we stop the running one
        // - Stop the running element
        TimelineElement running = mc.getRunningElement();
        mc.stopTimeline();
        if (running != null) { running.stop(); }
        mc.unscheduleEverything();
    }

    // Clear MissionControl and call the FlightController directly for these -
    // they mean the user wants flight to stop immediately
    public void autoLand() {
        forceStopMissionTimeline();
        fc.startLanding(null);
    }

    public void confirmLand() {
        forceStopMissionTimeline();
        fc.confirmLanding(null);
    }

    public void land(){
         mc.unscheduleEverything();
         mc.scheduleElement(new LandAction());
         mc.startTimeline();
    }

}
