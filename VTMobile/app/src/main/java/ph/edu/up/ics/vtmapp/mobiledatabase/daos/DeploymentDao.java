package ph.edu.up.ics.vtmapp.mobiledatabase.daos;

import android.util.Pair;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

import ph.edu.up.ics.vtmapp.mobiledatabase.dataobjects.DeploymentPairId;
import ph.edu.up.ics.vtmapp.mobiledatabase.tables.DeploymentTable;
import ph.edu.up.ics.vtmapp.rest.requestjsons.RecordJson;

@Dao
public interface DeploymentDao {
    @Query("SELECT DISTINCT operation_id, deployment_id FROM DeploymentTable")
    public List<DeploymentPairId> getAllDeploymentIdPairs();

    @Query("SELECT latitude, longitude, timestamp FROM DeploymentTable where operation_id = :operation_id and deployment_id = :deployment_id")
    public List<RecordJson> getRecordsByDeploymentIdPairs(int operation_id, int deployment_id);

    @Insert
    public void insertRecord(DeploymentTable recordTable);

    @Query("DELETE FROM DeploymentTable")
    public void nukeRecords();
}
