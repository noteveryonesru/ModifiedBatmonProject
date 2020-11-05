package ph.edu.up.ics.vtmapp.tools;

import android.content.Context;
import android.location.Location;
import android.preference.PreferenceManager;

import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ph.edu.up.ics.vtmapp.R;

public class Utils {
    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static final String KEY_REQUESTING_LOCATION_UPDATES = "requesting_location_updates";
    static final String KEY_LAST_OPERATION_ID = "last_session_id";

    public static int requestingLocationUpdates(Context context) {  //0 if not active, 1 if deployment, 2 if retrieval
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(KEY_REQUESTING_LOCATION_UPDATES, 0);
    }

    static void setRequestingLocationUpdates(Context context, int requestingLocationUpdates) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(KEY_REQUESTING_LOCATION_UPDATES, requestingLocationUpdates)
                .apply();
    }

    public static void incrementOperationId(Context context) {
        int newOperationId = getLastOperationId(context) + 1;
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(KEY_LAST_OPERATION_ID, newOperationId).apply();
    }
    public static int getLastOperationId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(KEY_LAST_OPERATION_ID, 0);
    }

    /**
     * Returns the {@code location} object as a human readable string.
     *
     * @param location The {@link Location}.
     */
    static String getLocationText(Location location) {
        return location == null ? "Unknown location" :
                "(" + location.getLatitude() + ", " + location.getLongitude() + ")";
    }

    static String getLocationTitle(Context context) {
        return context.getString(R.string.location_updated, DateFormat.getDateTimeInstance().format(new Date()));
    }

    static double calculateMetersPerSecond(double latitude1, double longitude1, double latitude2, double longitude2, double intervalMillis) {
        double dLat = Math.toRadians(latitude2 - latitude1);
        double dLon = Math.toRadians(longitude2 - longitude1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(latitude1))
                * Math.cos(Math.toRadians(latitude2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        long distanceInMeters = Math.round(6371000 * c);
        return distanceInMeters/intervalMillis;
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            //handle exception
        }
        return "";
    }

}
