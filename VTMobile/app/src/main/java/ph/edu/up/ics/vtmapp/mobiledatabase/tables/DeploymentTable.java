package ph.edu.up.ics.vtmapp.mobiledatabase.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import ph.edu.up.ics.vtmapp.Utils;

@Entity
public class DeploymentTable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "operation_id")
    public int operation_id;

    @ColumnInfo(name = "deployment_id")
    public int deployment_id;

    @ColumnInfo(name = "latitude")
    public double latitude;

    @ColumnInfo(name = "longitude")
    public double longitude;

    @ColumnInfo(name = "timestamp")
    public String timestamp;

    public DeploymentTable(int operation_id, int deployment_id, double latitude, double longitude) {
        this.operation_id = operation_id;
        this.deployment_id = deployment_id;
        this.latitude = latitude;
        this.longitude = longitude;
        Date date = new Date(System.currentTimeMillis());
        this.timestamp = Utils.simpleDateFormat.format(date);
    }
}
