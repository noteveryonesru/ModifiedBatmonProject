package ph.edu.up.ics.vtmapp.rest.requestjsons;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BodyJson {
    @SerializedName("MacAddress")
    public String macAddress;
    @SerializedName("ArrayOfOperations")
    public List<OperationJson> arrayOfOperations;

    public BodyJson(String macAddress, List<OperationJson> arrayOfOperations){
        this.macAddress = macAddress;
        this.arrayOfOperations = arrayOfOperations;
    }
}
