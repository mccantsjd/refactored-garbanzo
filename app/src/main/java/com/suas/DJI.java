package com.suas;

import dji.sdk.mission.MissionControl;
import dji.sdk.products.Aircraft;
import dji.sdk.sdkmanager.DJISDKManager;

public class DJI {
    public static Aircraft getAircraft() {
        return (Aircraft)DJISDKManager.getInstance().getProduct();
    }

    public static MissionControl getMissionControl() {
        return DJISDKManager.getInstance().getMissionControl();
    }
}
