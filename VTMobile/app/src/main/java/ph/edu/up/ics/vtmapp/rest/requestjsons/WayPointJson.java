package ph.edu.up.ics.vtmapp.rest.requestjsons;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class WayPointJson {
    @SerializedName("Latitude")
    @ColumnInfo(name = "latitude")
    public double latitude;

    @SerializedName("Longitude")
    @ColumnInfo(name = "longitude")
    public double longitude;

    @SerializedName("TimeStamp")
    @ColumnInfo(name = "timestamp")
    public String timestamp;
}
