package in.geofriend.androidutils.location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import in.geofriend.androidutils.R;
import in.geofriend.locationhelper.LocationHelper;
import in.geofriend.permissions.AppPermissionManager;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        LocationHelper locationHelper = new LocationHelper(this);
        AppPermissionManager appPermissionManager = new AppPermissionManager(this);
        String []requiredPermissions = new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        if(!appPermissionManager.hasAllPermissions(requiredPermissions)) {
            appPermissionManager.requestForPermissions(requiredPermissions, null);
        }
        findViewById(R.id.btn_get_current_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationHelper.fetchLastKnownLocation(new LocationHelper.LocationUpdateListener() {
                    @Override
                    public void onLocationUpdated(int status, Location location) {
                        if(status == LocationHelper.STATUS_OK) {
                            ((TextView)findViewById(R.id.location_display)).setText(getString(R.string.current_location, location));
                        }
                    }
                });
            }
        });

        findViewById(R.id.btn_get_last_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location location = locationHelper.getLastKnownLocation();
                if(location != null) {
                    ((TextView)findViewById(R.id.location_display)).setText(getString(R.string.last_location, location));
                } else {
                    ((TextView)findViewById(R.id.location_display)).setText(R.string.last_location_unavailable);
                }
            }
        });
    }

    private void checkLocationPermission() {
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 0);
        }
    }
}