package ph.edu.up.ics.vtmapp.rest.requestjsons;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RetrievalJson {
    @SerializedName("StartTime")
    public String startTime;
    @SerializedName("EndTime")
    public String endTime;
    @SerializedName("ArrayOfRecords")
    public List<RecordJson> arrayOfRecords;
    @SerializedName("ArrayOfWaypoints")
    public List<WayPointJson> arrayOfWaypoints;

    public RetrievalJson(String startTime, String endTime, List<RecordJson>arrayOfRecords, List<WayPointJson> arrayOfWaypoints){
        this.startTime = startTime;
        this.endTime = endTime;
        this.arrayOfRecords = arrayOfRecords;
        this.arrayOfWaypoints = arrayOfWaypoints;
    }
}
