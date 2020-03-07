package com.dji.sdk.sample.navigation;

import android.util.Log;

import androidx.annotation.NonNull;

import com.dji.sdk.sample.internal.utils.ModuleVerificationUtil;

import dji.common.flightcontroller.ObstacleDetectionSector;
import dji.common.flightcontroller.VisionDetectionState;
import dji.common.flightcontroller.VisionSensorPosition;
import dji.sdk.flightcontroller.FlightAssistant;
import dji.sdk.mission.timeline.triggers.Trigger;

public class ObjectDetectionTrigger extends Trigger {

    // Distance in meters, below which is considered dangerous
    // NOTE (Aaron) - Readings below 1m get increasingly erratic and unreliable,
    // at least during my testing at home. The docs say the sensors ought to be
    // accurate down to 0.5m, so it might have been an ambient lighting issue.
    private static final Float MIN_SAFE_DISTANCE = 1.0f;
    // Number of sectors (out of 4) that must be at or below MIN_SAFE_DISTANCE
    // to cause a trigger
    private static final int MIN_UNSAFE_SECTORS = 3;

    private FlightAssistant fa;

    public ObjectDetectionTrigger(Action a) {
        action = a;
        fa = ModuleVerificationUtil.getFlightController().getFlightAssistant();
    }

    @Override
    public void start() {
        super.start();

        fa.setVisionDetectionStateUpdatedCallback(new VisionDetectionState.Callback() {
            @Override
            public void onUpdate(@NonNull VisionDetectionState state) {
                if (state.getPosition() == VisionSensorPosition.NOSE) {
                    ObstacleDetectionSector[] sectors = state.getDetectionSectors();

                    int sectorsReportingDanger = 0;
                    for (ObstacleDetectionSector sector: sectors) {
                        float distance = sector.getObstacleDistanceInMeters();
                        if (distance < MIN_SAFE_DISTANCE) ++sectorsReportingDanger;
                    }

                    if (sectorsReportingDanger >= MIN_UNSAFE_SECTORS) {
                        Log.d("suas.trigger", "Object detected");
                        triggerAction();
                        stop(); // This trigger should only fire once
                    }
                }
            }
        });
    }


    @Override
    public void stop() {
        fa.setVisionDetectionStateUpdatedCallback(null);
        super.stop();
    }
}
