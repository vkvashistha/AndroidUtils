# AndroidUtils - Location Helper
Quick utility to fetch last known location


## Usage

### Step 1: Import library

```agsl
implement io.github.geofriendtech:locationhelper:1.0.0
```

### Step 2: Request Location Permission. You can use your own implementation or can use ``permissions`` package in this project as follows:
```agsl
AppPermissionManager appPermissionManager = new AppPermissionManager(this);
        String []requiredPermissions = new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        if(!appPermissionManager.hasAllPermissions(requiredPermissions)) {
            appPermissionManager.requestForPermissions(requiredPermissions, null);
        }
```
### Step 3: Initialise ImagePicker in `Activity.onCreate()` or `Fragment.onCreate()` method

```agsl
LocationHelper locationHelper = new LocationHelper(this);
```

### Step 4: Fetch Last Known Location

```agsl
imagePicker.pickImage(new ImagePicker.ImageResultCallback() {
                    @Override
                    public void onImageResult(boolean success, Intent resultIntent, Uri imagePath) {
                        ImageView imageView = findViewById(R.id.iv_image);
                        imageView.setImageURI(imagePath);
                    }
                });locationHelper.fetchLastKnownLocation(new LocationHelper.LocationUpdateListener() {
                    @Override
                    public void onLocationUpdated(int status, Location location) {
                        if(status == LocationHelper.STATUS_OK) {
                            ((TextView)findViewById(R.id.location_display)).setText(getString(R.string.current_location, location));
                        }
                    }
                });
```

## Future Roadmap
Real Time Location