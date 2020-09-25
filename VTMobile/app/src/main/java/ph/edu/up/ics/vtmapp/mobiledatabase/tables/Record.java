package ph.edu.up.ics.vtmapp.mobiledatabase.tables;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import ph.edu.up.ics.vtmapp.Utils;

@Entity
public class Record {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "session_id")
    public int session_id;

    @ColumnInfo(name = "latitude")
    public double latitude;

    @ColumnInfo(name = "longitude")
    public double longitude;

    @ColumnInfo(name = "timestamp")
    public String timestamp;

    public Record(int session_id, double latitude, double longitude, double heading, double speed){
        this.session_id = session_id;
        this.latitude = latitude;
        this.longitude = longitude;
        Date date = new Date(System.currentTimeMillis());
        this.timestamp = Utils.simpleDateFormat.format(date);
    }

}
