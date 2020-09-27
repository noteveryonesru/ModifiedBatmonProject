package ph.edu.up.ics.vtmapp.mobiledatabase.daos;

import android.util.Pair;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

import ph.edu.up.ics.vtmapp.mobiledatabase.dataobjects.RetrievalPairId;
import ph.edu.up.ics.vtmapp.mobiledatabase.tables.RetrievalTable;
import ph.edu.up.ics.vtmapp.rest.requestjsons.RecordJson;

@Dao
public interface RetrievalDao {
    @Query("SELECT DISTINCT operation_id, retrieval_id FROM RetrievalTable")
    public List<RetrievalPairId> getAllRetrievalIdPairs();

    @Query("SELECT latitude, longitude, timestamp FROM RetrievalTable where operation_id = :operation_id and retrieval_id = :retrieval_id")
    public List<RecordJson> getRecordsByRetrievalIdPairs(int operation_id, int retrieval_id);

    @Insert
    public void insertRecord(RetrievalTable recordTable);

    @Query("DELETE FROM RetrievalTable")
    public void nukeRecords();
}
