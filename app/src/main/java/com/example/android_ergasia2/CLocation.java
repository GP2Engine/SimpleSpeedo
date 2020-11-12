package com.example.android_ergasia2;

import android.location.Location;

/*
Kollias Anastasios MPSP19018
Ergasia 2 - Android
 */

public class CLocation extends Location {

    private boolean UseMetricUnits = false;

    public CLocation(Location location)
    {
        this(location, true);
    }

    public CLocation(Location location, boolean UseMetricUnits)
    {
        super(location);
        this.UseMetricUnits = UseMetricUnits;
    }

    public boolean getUseMetricUnits()
    {
        return this.UseMetricUnits;
    }

    public void setUseMetricUnits(boolean UseMetricUnits)
    {
        this.UseMetricUnits = UseMetricUnits;
    }

    @Override
    public float distanceTo(Location dest) {
        float nDistance = super.distanceTo(dest);
        if(!this.getUseMetricUnits()){
            nDistance = nDistance * 3.28083989501312f;
        }
        return nDistance;
    }

    @Override
    public double getAltitude() {
        double nAltitude = super.getAltitude();
        if(!this.getUseMetricUnits()){
            nAltitude = nAltitude * 3.28083989501312d;
        }
        return nAltitude;
    }

    @Override
    public float getSpeed() {
        float nSpeed = super.getSpeed() * 3.6f;
        if(!this.getUseMetricUnits()){
            nSpeed = super.getSpeed() * 2.23693629f;
        }
        return nSpeed;
    }

    @Override
    public float getAccuracy() {
        float nAccuracy = super.getAccuracy();
        if(!this.getUseMetricUnits()){
            nAccuracy = nAccuracy * 3.28083989501312f;
        }
        return nAccuracy;
    }
}
