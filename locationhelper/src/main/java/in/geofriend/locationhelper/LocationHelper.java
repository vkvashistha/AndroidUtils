package in.geofriend.locationhelper;

import static android.content.Context.LOCATION_SERVICE;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import java.util.List;

/**
 * This class is a wrapper on LocationManager to provide current location in simplified manner
 */
public class LocationHelper {
    public static int STATUS_LOCATION_PERMISSION_NOT_GRANTED = 1;
    public static int STATUS_OK = 0;
    private Context context;
    private Location lastKnownLocation;
    private LocationUpdateListener listener;

    public LocationHelper(Context context) {
        this.context = context;
    }

    /**
     * Fetch latest device location.
     * @param listener - callback to receive location object
     */
    public void fetchLastKnownLocation(LocationUpdateListener listener) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            listener.onLocationUpdated(STATUS_LOCATION_PERMISSION_NOT_GRANTED, null);
        }

        LocationManager locationManager = (LocationManager) context
                .getSystemService(LOCATION_SERVICE);

        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                locationManager.requestLocationUpdates(provider, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        lastKnownLocation = location;
                        listener.onLocationUpdated(STATUS_OK, lastKnownLocation);
                    }
                });
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        if(bestLocation != null) {
            lastKnownLocation = bestLocation;
        }
        if (listener != null && lastKnownLocation != null) {
            listener.onLocationUpdated(STATUS_OK, lastKnownLocation);
        }
    }

    /**
     * Call #fetchCurrentLocation() method first. Later on you can use this method to get last
     * fetched location synchronously
     * @return Location object fetched when #fetchCurrentLocation() being called.
     */
    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    public interface LocationUpdateListener {
        void onLocationUpdated(int status, Location location);
    }
}
