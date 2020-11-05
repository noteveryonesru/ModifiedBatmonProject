package ph.edu.up.ics.vtmapp.mobiledatabase.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import ph.edu.up.ics.vtmapp.tools.Utils;

@Entity
public class TrackTable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "operationid")
    public int operationId;

    @ColumnInfo(name = "latitude")
    public double latitude;

    @ColumnInfo(name = "longitude")
    public double longitude;

    @ColumnInfo(name = "speed")
    public double speed;

    @ColumnInfo(name = "heading")
    public double heading;

    @ColumnInfo(name = "timestamp")
    public String timestamp;

    @ColumnInfo(name = "iswaypoint")
    public boolean isWayPoint;

    @ColumnInfo(name = "isdeploy")
    public boolean isDeploy;

    public TrackTable(int operationId, double latitude, double longitude, double speed, double heading, boolean isWayPoint, boolean isDeploy) {
        this.operationId = operationId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.heading = heading;
        this.isWayPoint = isWayPoint;
        this.isDeploy = isDeploy;
        Date date = new Date(System.currentTimeMillis());
        this.timestamp = Utils.simpleDateFormat.format(date);
    }
}
