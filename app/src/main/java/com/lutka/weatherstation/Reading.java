package com.lutka.weatherstation;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Paulina on 16/11/2014.
 */
public class Reading
{
    @SerializedName("TypeID")
   String typeId; //H,T

    @SerializedName("SpecID")
   int specId;

    @SerializedName("SensorID")
   int sensorId;

    @SerializedName("LocationID")
   int locationId;

    @SerializedName("ReadingID")
   int readingId;

    @SerializedName("Time")
   int time;

    @SerializedName("Value")
   int value;

    @SerializedName("Latitude")
   double latitude;

    @SerializedName("Longitude")
   double longitude;

    @SerializedName("Type")
   String locationType; //i, o

    @SerializedName("Comment")
   String locationComment;

    @SerializedName("DeviceID")
   int deviceId;

    @SerializedName("Model")
   String sensorModel;

    @SerializedName("Accuracy")
   int sensorAccuracy;

    @SerializedName("Minimum")
   int sensorMinimum;

    @SerializedName("Maximum")
   int sensorMaximum;

    @SerializedName("Unit")
   String measurementUnit;

    public String getTypeId()
    {
        return typeId;
    }

    public int getSpecId()
    {
        return specId;
    }

    public int getSensorId()
    {
        return sensorId;
    }

    public int getLocationId()
    {
        return locationId;
    }

    public int getReadingId()
    {
        return readingId;
    }

    public int getTime()
    {
        return time;
    }

    @Override
    public String toString()
    {
        return "Reading{" +
                "value=" + value +
                ", typeId='" + typeId + '\'' +
                ", locationComment='" + locationComment + '\'' +
                ", measurementUnit='" + measurementUnit + '\'' +
                '}';
    }

    public int getValue()
    {
        return value;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public String getLocationType()
    {
        return locationType;
    }

    public String getLocationComment()
    {
        return locationComment;
    }

    public int getDeviceId()
    {
        return deviceId;
    }

    public String getSensorModel()
    {
        return sensorModel;
    }

    public int getSensorAccuracy()
    {
        return sensorAccuracy;
    }

    public int getSensorMinimum()
    {
        return sensorMinimum;
    }

    public int getSensorMaximum()
    {
        return sensorMaximum;
    }

    public String getMeasurementUnit()
    {
        return measurementUnit;
    }
}



