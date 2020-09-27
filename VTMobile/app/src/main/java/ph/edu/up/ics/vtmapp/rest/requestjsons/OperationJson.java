package ph.edu.up.ics.vtmapp.rest.requestjsons;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OperationJson {
    @SerializedName("StartTime")
    public String startTime;
    @SerializedName("EndTime")
    public String endTime;
    @SerializedName("ArrayOfDeployments")
    public List<DeploymentJson> arrayOfDeployments;
    @SerializedName("ArrayOfRetrievals")
    public List<RetrievalJson> arrayOfRetrievals;
    @SerializedName("ArrayOfTags")
    public List<String> arrayOfTags;

    public OperationJson(String startTime, String endTime, List<DeploymentJson> arrayOfDeployments, List<RetrievalJson> arrayOfRetrievals, List<String> arrayOfTags){
        this.startTime = startTime;
        this.endTime = endTime;
        this.arrayOfDeployments = arrayOfDeployments;
        this.arrayOfRetrievals = arrayOfRetrievals;
        this.arrayOfTags = arrayOfTags;
    }
}
