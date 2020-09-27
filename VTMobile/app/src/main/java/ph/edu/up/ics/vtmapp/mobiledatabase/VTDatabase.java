package ph.edu.up.ics.vtmapp.mobiledatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ph.edu.up.ics.vtmapp.mobiledatabase.daos.DeploymentDao;
import ph.edu.up.ics.vtmapp.mobiledatabase.daos.DeploymentWaypointDao;
import ph.edu.up.ics.vtmapp.mobiledatabase.daos.RetrievalDao;
import ph.edu.up.ics.vtmapp.mobiledatabase.daos.RetrievalWaypointDao;
import ph.edu.up.ics.vtmapp.mobiledatabase.tables.DeploymentTable;
import ph.edu.up.ics.vtmapp.mobiledatabase.tables.DeploymentWaypointTable;
import ph.edu.up.ics.vtmapp.mobiledatabase.tables.RetrievalTable;
import ph.edu.up.ics.vtmapp.mobiledatabase.tables.RetrievalWaypointTable;

@Database(entities = {DeploymentTable.class, DeploymentWaypointTable.class, RetrievalTable.class, RetrievalWaypointTable.class}, version = 1)
public abstract class VTDatabase extends RoomDatabase {
    private static VTDatabase vtDatabaseInstance = null;
    public abstract DeploymentDao deploymentDao();
    public abstract DeploymentWaypointDao deploymentWaypointDao();
    public abstract RetrievalDao retrievalDao();
    public abstract RetrievalWaypointDao retrievalWaypointDao();

    public static VTDatabase getInstance(Context context){
        if (vtDatabaseInstance == null){
            vtDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(), VTDatabase.class, "vtmapp-db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return vtDatabaseInstance;
    }

    public static class AsyncInsertDeploymentRecord extends AsyncTask<Void, Void, Void> {
        private VTDatabase db;
        private int operationId;
        private int deploymentId;
        private double latitude;
        private double longitude;

        public AsyncInsertDeploymentRecord(VTDatabase db, int operationId, int deploymentId, double latitude, double longitude){
            this.db = db;
            this.operationId = operationId;
            this.deploymentId = deploymentId;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.deploymentDao().insertRecord(new DeploymentTable(operationId, deploymentId, latitude, longitude));
            return null;
        }
    }

    public static class AsyncInsertDeploymentWaypointRecord extends AsyncTask<Void, Void, Void> {
        private VTDatabase db;
        private int operationId;
        private int deploymentId;
        private double latitude;
        private double longitude;

        public AsyncInsertDeploymentWaypointRecord(VTDatabase db, int operationId, int deploymentId, double latitude, double longitude){
            this.db = db;
            this.operationId = operationId;
            this.deploymentId = deploymentId;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.deploymentWaypointDao().insertRecord(new DeploymentWaypointTable(operationId, deploymentId, latitude, longitude));
            return null;
        }
    }

    public static class AsyncInsertRetrievalRecord extends AsyncTask<Void, Void, Void> {
        private VTDatabase db;
        private int operationId;
        private int retrievalId;
        private double latitude;
        private double longitude;

        public AsyncInsertRetrievalRecord(VTDatabase db, int operationId, int retrievalId, double latitude, double longitude){
            this.db = db;
            this.operationId = operationId;
            this.retrievalId = retrievalId;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.retrievalDao().insertRecord(new RetrievalTable(operationId, retrievalId, latitude, longitude));
            return null;
        }
    }

    public static class AsyncInsertRetrievalWaypointRecord extends AsyncTask<Void, Void, Void> {
        private VTDatabase db;
        private int operationId;
        private int retrievalId;
        private double latitude;
        private double longitude;

        public AsyncInsertRetrievalWaypointRecord(VTDatabase db, int operationId, int retrievalId, double latitude, double longitude){
            this.db = db;
            this.operationId = operationId;
            this.retrievalId = retrievalId;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.retrievalWaypointDao().insertRecord(new RetrievalWaypointTable(operationId, retrievalId, latitude, longitude));
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
            db.deploymentDao().nukeRecords();
            db.deploymentWaypointDao().nukeRecords();
            db.retrievalDao().nukeRecords();
            db.retrievalWaypointDao().nukeRecords();
            return null;
        }
    }
}
