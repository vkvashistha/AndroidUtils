# AndroidUtils - Camerahelper
Quick utility to launch camera and capture image


## Usage

### Step 1: Import library

```agsl
implement io.github.geofriendtech:camerahelper:1.0.0
```

### Step 2: Initialise BarcodeScanner in `Activity.onCreate()` or `Fragment.onCreate()` method

```agsl
final CameraHelper cameraHelper = new CameraHelper(this);
```

### Step 3: Add Storage Provider file

```agsl
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
<!-- For saving data in external directory (note: you need to ask user to choose destination directory) -->
<external-path name="my_external" path="/" />

<!-- For saving data in cache directory -->
<cache-path
    name="my_cache"
    path="/"/>
    
<!-- To save file in Internal Storage or App package-->
<files-path
    name="app_internal"
    path="/"/>
</paths>
```

### Step 4: Start Camera to capture Image

```agsl
cameraHelper.takePhoto("photo.jpg", new CameraHelper.CameraResultCallback() {
                    @Override
                    public void onCameraResult(boolean success, Intent resultIntent, Uri imagePath) {
                        ((ImageView)findViewById(R.id.camera_output)).setImageURI(imagePath);
                    }
                });
```
"photo.jpg" in above code will be store in App's private storage. Or you can provide your own Camera Intent to store the image at desired location

```agsl
File imageFile = context.getCacheDir();
Uri imagePath =  FileProvider.getUriForFile(context.getApplicationContext(),
                context.getPackageName()+".provider",
                imageFile);   // Or get Uri by asking user for destination directory
Intent cameraIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imagePath);
        
cameraHelper.takePhoto(cameraIntent, myCallback);
```




## Future Roadmap
Add support for QR Code with various formats