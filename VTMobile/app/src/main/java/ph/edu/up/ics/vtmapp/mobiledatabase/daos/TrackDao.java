package ph.edu.up.ics.vtmapp.mobiledatabase.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

import ph.edu.up.ics.vtmapp.mobiledatabase.tables.TrackTable;
import ph.edu.up.ics.vtmapp.rest.requestjsons.TrackJson;

@Dao
public interface TrackDao {
    @Query("SELECT DISTINCT operationId FROM TrackTable")
    public List<Integer> getAllOperationIds();

    @Query("SELECT latitude, longitude, speed, heading, timestamp, iswaypoint, isdeploy FROM TrackTable where operationId = :operationId")
    public List<TrackJson> getRecordsByOperationId(int operationId);

    @Insert
    public void insertRecord(TrackTable recordTable);

    @Query("DELETE FROM TrackTable")
    public void nukeRecords();
}
