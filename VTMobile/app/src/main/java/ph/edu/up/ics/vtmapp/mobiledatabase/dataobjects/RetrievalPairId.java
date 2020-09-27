package ph.edu.up.ics.vtmapp.mobiledatabase.dataobjects;

import com.google.gson.annotations.SerializedName;

public class RetrievalPairId {
    @SerializedName("operation_id")
    public int operation_id;
    @SerializedName("retrieval_id")
    public int retrieval_id;

    public RetrievalPairId(int operation_id, int retrieval_id) {
        this.operation_id = operation_id;
        this.retrieval_id = retrieval_id;
    }
}
