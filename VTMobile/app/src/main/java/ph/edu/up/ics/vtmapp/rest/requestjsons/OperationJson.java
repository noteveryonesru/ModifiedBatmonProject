package ph.edu.up.ics.vtmapp.rest.requestjsons;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OperationJson {
    @SerializedName("startTime")
    public String startTime;
    @SerializedName("endTime")
    public String endTime;
    @SerializedName("gearName")
    public String gearName;
    @SerializedName("tracks")
    public List<TrackJson> tracks;
    @SerializedName("tags")
    public List<String> tags;

    public OperationJson(String startTime, String endTime, String gearName, List<TrackJson> tracks, List<String> tags){
        this.startTime = startTime;
        this.endTime = endTime;
        this.gearName = gearName;
        this.tracks = tracks;
        this.tags = tags;
    }
}
