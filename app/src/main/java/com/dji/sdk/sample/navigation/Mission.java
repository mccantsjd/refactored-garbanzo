package com.dji.sdk.sample.navigation;

//import dji.sdk.mission.MissionControl;
import dji.sdk.mission.timeline.TimelineElement;

import android.util.Pair;
import org.json.JSONObject;
import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;

// perhaps singleton to enforce only one mission at a time

public class Mission {

    private String units;
    private Pair<Integer, Integer> xBounds;
    private Pair<Integer, Integer> yBounds;
    private Pair<Integer, Integer> zBounds;
    private Coordinate start;
    private Coordinate end;
    private String startPosition;
    public ArrayList<TimelineElement> movements;

    Mission() {
        // empty constructor
    }

    Mission(String units,
            Pair<Integer,Integer> xBounds,
            Pair<Integer,Integer> yBounds,
            Pair<Integer,Integer> zBounds,
            Coordinate start, Coordinate end,
            ArrayList<TimelineElement> movements,
            String startPosition) {
        this.units = units;
        this.xBounds = xBounds;
        this.yBounds = yBounds;
        this.zBounds = zBounds;
        this.start = start;
        this.end = end;
        this.movements = movements;
        this.startPosition = startPosition;
    }

    public ArrayList<TimelineElement> getMovements() {
        return this.movements;
    }

}
