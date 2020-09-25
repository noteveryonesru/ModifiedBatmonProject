package ph.edu.up.ics.vtmapp.rest.requestjsons;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SessionJson {
    @SerializedName("StartTime")
    public String startTime;
    @SerializedName("EndTime")
    public String endTime;
    @SerializedName("ArrayOfRecords")
    public List<RecordJson> arrayOfRecords;

    public SessionJson(String startTime, String endTime, List<RecordJson>arrayOfRecords){
        this.startTime = startTime;
        this.endTime = endTime;
        this.arrayOfRecords = arrayOfRecords;
    }
}
