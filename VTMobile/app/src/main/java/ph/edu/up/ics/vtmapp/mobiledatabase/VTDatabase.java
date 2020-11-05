package ph.edu.up.ics.vtmapp.mobiledatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ph.edu.up.ics.vtmapp.mobiledatabase.daos.TrackDao;
import ph.edu.up.ics.vtmapp.mobiledatabase.tables.TrackTable;

@Database(entities = {TrackTable.class}, version = 2)
public abstract class VTDatabase extends RoomDatabase {
    private static VTDatabase vtDatabaseInstance = null;
    public abstract TrackDao trackDao();

    public static VTDatabase getInstance(Context context){
        if (vtDatabaseInstance == null){
            vtDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(), VTDatabase.class, "vtmapp-db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return vtDatabaseInstance;
    }

    public static class AsyncInsertTrack extends AsyncTask<Void, Void, Void> {
        private VTDatabase db;
        private int operationId;
        private double latitude;
        private double longitude;
        private double speed;
        private double heading;
        private boolean isWayPoint;
        private boolean isDeploy;

        public AsyncInsertTrack(VTDatabase db, int operationId, double latitude, double longitude, double speed, double heading, boolean isWayPoint, boolean isDeploy){
            this.db = db;
            this.operationId = operationId;
            this.latitude = latitude;
            this.longitude = longitude;
            this.speed = speed;
            this.heading = heading;
            this.isWayPoint = isWayPoint;
            this.isDeploy = isDeploy;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.trackDao().insertRecord(new TrackTable(operationId, latitude, longitude, speed, heading, isWayPoint, isDeploy));
            return null;
        }
    }

    public static class AsyncNuke extends AsyncTask<Void, Void, Void>{
        private VTDatabase db;
        public AsyncNuke(VTDatabase db){
            this.db = db;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            db.trackDao().nukeRecords();
            return null;
        }
    }
}
