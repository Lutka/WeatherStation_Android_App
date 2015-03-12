package com.lutka.weatherstation;

import com.google.gson.annotations.SerializedName;

/** Class which represent a sensor reading its parameters are as follow:typeId (H/T), specId, sensorId,
 * locationId, readingId, time, value, latitude, longitude, locationType (i/o), locationComment,
 * deviceId, sensorModel, sensorAccuracy, sensorMinimum, sensorMaximum, measurementUnit
 *
 * @author Paulina
 * @version 1.0
 * @since 16/11/2014
 * */
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
                ", time='" + (new java.util.Date((long)time*1000)).toString() + '\'' +
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



