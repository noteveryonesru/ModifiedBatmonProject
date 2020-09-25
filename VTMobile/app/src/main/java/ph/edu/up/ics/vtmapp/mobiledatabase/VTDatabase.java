package ph.edu.up.ics.vtmapp.mobiledatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ph.edu.up.ics.vtmapp.mobiledatabase.daos.RecordDao;
import ph.edu.up.ics.vtmapp.mobiledatabase.tables.Record;

@Database(entities = {Record.class}, version = 1)
public abstract class VTDatabase extends RoomDatabase {
    private static VTDatabase vtDatabaseInstance = null;
    public abstract RecordDao recordDao();

    public static VTDatabase getInstance(Context context){
        if (vtDatabaseInstance == null){
            vtDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(), VTDatabase.class, "vtmapp-db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return vtDatabaseInstance;
    }

    public static class AsyncInsertRecord extends AsyncTask<Void, Void, Void> {
        private VTDatabase db;
        private int sessionId;
        private double latitude;
        private double longitude;
        private double heading;
        private double speed;

        public AsyncInsertRecord(VTDatabase db, int sessionId, double latitude, double longitude, double heading, double speed){
            this.db = db;
            this.sessionId = sessionId;
            this.latitude = latitude;
            this.longitude = longitude;
            this.heading = heading;
            this.speed = speed;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.recordDao().insertRecord(new Record(sessionId, latitude, longitude, heading, speed));
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
            db.recordDao().nukeRecords();
            return null;
        }
    }
}
