package ph.edu.up.ics.vtmapp.mobiledatabase.dataobjects;

import com.google.gson.annotations.SerializedName;

public class DeploymentPairId {
    @SerializedName("operation_id")
    public int operation_id;
    @SerializedName("deployment_id")
    public int deployment_id;

    public DeploymentPairId(int operation_id, int deployment_id){
        this.operation_id = operation_id;
        this.deployment_id = deployment_id;
    }
}
