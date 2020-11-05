package ph.edu.up.ics.vtmapp.rest.requestjsons;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class TrackJson {
    @SerializedName("latitude")
    @ColumnInfo(name = "latitude")
    public double latitude;

    @SerializedName("longitude")
    @ColumnInfo(name = "longitude")
    public double longitude;

    @SerializedName("speed")
    @ColumnInfo(name = "speed")
    public double speed;

    @SerializedName("heading")
    @ColumnInfo(name = "heading")
    public double heading;

    @SerializedName("timestamp")
    @ColumnInfo(name = "timestamp")
    public String timestamp;

    @SerializedName("isWayPoint")
    @ColumnInfo(name = "iswaypoint")
    public boolean isWayPoint;

    @SerializedName("isDeploy")
    @ColumnInfo(name = "isdeploy")
    public boolean isDeploy;

}
