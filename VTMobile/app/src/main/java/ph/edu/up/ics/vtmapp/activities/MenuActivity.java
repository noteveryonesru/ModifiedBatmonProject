package ph.edu.up.ics.vtmapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import ph.edu.up.ics.vtmapp.R;
import ph.edu.up.ics.vtmapp.tools.Utils;
import ph.edu.up.ics.vtmapp.tools.VTMappLocationService;

public class MenuActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1001;

    // The BroadcastReceiver used to listen from broadcasts from the service.
    private VTMappBroadcastReceiver vtMappBroadcastReceiver;

    // A reference to the service used to get location updates.
    private VTMappLocationService vtMappLocationService = null;

    // Tracks the bound state of the service.
    private boolean mBound = false;

    private Button deployButton;
    private Button retrieveButton;
    private Button waypointButton;
    private Button syncButton;
    private double lastLocationLatitude = 0;
    private double lastLocationLongitude = 0;
    private boolean hasRetrievedLastLocation = false;
    private MenuActivity selff = this;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            VTMappLocationService.LocalBinder binder = (VTMappLocationService.LocalBinder) service;
            vtMappLocationService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            vtMappLocationService = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        deployButton = findViewById(R.id.deployButton);
        retrieveButton = findViewById(R.id.retrieveButton);
        waypointButton = findViewById(R.id.waypointButton);
        syncButton = findViewById(R.id.syncButton);
        if (Utils.requestingLocationUpdates(this) != 0) {
            if (!checkPermissions()) {
                requestPermissions();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

        deployButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermissions()) {
                    requestPermissions();
                } else {
                    vtMappLocationService.requestLocationUpdates(1);
                }
            }
        });

        retrieveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermissions()) {
                    requestPermissions();
                } else {
                    vtMappLocationService.requestLocationUpdates(0);
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vtMappLocationService.removeLocationUpdates();
            }
        });

        final String macAddress = getMacAddr();

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = urlBox.getText().toString();
                if (!url.isEmpty()) {
                    lock();
                    OceansClient oceansClient = new OceansClient(url);
                    oceansClient.uploadRecords(MainActivity.this, OceansDatabase.getInstance(MainActivity.this), macAddress);
                } else {
                    mainToast("Url cannot be empty");
                }
            }
        });

        catchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasRetrievedLastLocation) {
                    OceansDatabase.AsyncInsertCatch runner = new OceansDatabase.AsyncInsertCatch(OceansDatabase.getInstance(MainActivity.this), Utils.getLastSessionId(MainActivity.this), lastLocationLatitude, lastLocationLongitude, "Tuna");
                    runner.execute();
                    mainToast("Catch Recorded!");
                } else {
                    mainToast("Latest location has not been processed yet.");
                }
            }
        });

        // Restore the state of the buttons when the activity (re)launches.
        setButtonsState(Utils.requestingLocationUpdates(this));

        // Bind to the service. If the service is in foreground mode, this signals to the service
        // that since this activity is in the foreground, the service can exit foreground mode.
        bindService(new Intent(this, OceansLocationService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(oceansBroadcastReceiver, new IntentFilter(OceansLocationService.ACTION_BROADCAST));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(oceansBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (mBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            unbindService(mServiceConnection);
            mBound = false;
        }
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onStop();
    }

    private boolean checkPermissions() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (shouldProvideRationale) {
            Snackbar.make(findViewById(R.id.activity_main), R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(MenuActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    }).show();
        } else {
            Log.i(TAG, "Requesting permission");
            ActivityCompat.requestPermissions(MenuActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
                vtMappLocationService.requestLocationUpdates();
            } else {
                // Permission denied.
                setButtonsState(false);
                Snackbar.make(
                        findViewById(R.id.activity_main),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }).show();
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        // Update the buttons state depending on whether location updates are being requested.
        if (s.equals(Utils.KEY_REQUESTING_LOCATION_UPDATES)) {
            setButtonsState(sharedPreferences.getBoolean(Utils.KEY_REQUESTING_LOCATION_UPDATES, false));
        }
    }

    private void updateTextViews(Location location){
        String latitudeString = "" + location.getLatitude();
        String longitudeString = "" + location.getLongitude();
        String speedString = String.format("%.3f", location.getSpeed()*3.6) + "km/hr";
        //String speedString = location.getSpeed() + "m/s";
        String headingString = "" + location.getBearing();
        latitudeTextView.setText(latitudeString);
        longitudeTextView.setText(longitudeString);
        speedTextView.setText(speedString);
        headingTextView.setText(headingString);
    }

    private void setButtonsState(int requestingLocationUpdates) {
        if (requestingLocationUpdates == 0) {//inactive

        } else if (requestingLocationUpdates == 1){//deployment

        }else if (requestingLocationUpdates == 2){//retrieval

        }
    }

    @Override
    public void lock() {
        startButton.setEnabled(false);
        stopButton.setEnabled(false);
        uploadButton.setEnabled(false);
        catchButton.setEnabled(false);
    }

    @Override
    public void unlock() {
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        uploadButton.setEnabled(true);
        catchButton.setEnabled(false);
    }

    public void storeLastLocation(Location lastLocation) {
        this.hasRetrievedLastLocation = true;
        this.lastLocationLatitude = lastLocation.getLatitude();
        this.lastLocationLongitude = lastLocation.getLongitude();
    }

    public void mainToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void toastError(String s) {
        mainToast(s);
    }

    private class VTMappBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(VTMappLocationService.EXTRA_LOCATION);
            if (location != null) {
                //Toast.makeText(MainActivity.this, Utils.getLocationText(location), Toast.LENGTH_SHORT).show();
                //AsyncToast runner = new AsyncToast(OceansDatabase.getInstance(context), new WeakReference<MainActivity>(MainActivity.this));
                //runner.execute(MainActivity.this);
                //List<Integer> ids = OceansDatabase.getInstance(context).recordDao().getIds();
                //Toast.makeText(MainActivity.this, ids.toString(), Toast.LENGTH_LONG).show();
                storeLastLocation(location);
                updateTextViews(location);
            }
        }
    }
}