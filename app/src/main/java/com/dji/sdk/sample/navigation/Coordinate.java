package com.dji.sdk.sample.navigation;

import java.util.Locale;

public class Coordinate {
    private int x;
    private int y;
    private int z;

    Coordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "(%d,%d,%d)", x, y, z);
    }
}
