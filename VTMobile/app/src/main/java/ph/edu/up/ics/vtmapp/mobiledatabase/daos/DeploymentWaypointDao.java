package ph.edu.up.ics.vtmapp.mobiledatabase.daos;

import android.util.Pair;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

import ph.edu.up.ics.vtmapp.mobiledatabase.dataobjects.DeploymentPairId;
import ph.edu.up.ics.vtmapp.mobiledatabase.tables.DeploymentWaypointTable;
import ph.edu.up.ics.vtmapp.rest.requestjsons.WayPointJson;

@Dao
public interface DeploymentWaypointDao {
    @Query("SELECT DISTINCT operation_id, deployment_id FROM DeploymentWaypointTable")
    public List<DeploymentPairId> getAllDeploymentIdPairs();

    @Query("SELECT latitude, longitude, timestamp FROM DeploymentWaypointTable where operation_id = :operation_id and deployment_id = :deployment_id")
    public List<WayPointJson> getRecordsByDeploymentIdPairs(int operation_id, int deployment_id);

    @Insert
    public void insertRecord(DeploymentWaypointTable recordTable);

    @Query("DELETE FROM DeploymentWaypointTable")
    public void nukeRecords();
}
