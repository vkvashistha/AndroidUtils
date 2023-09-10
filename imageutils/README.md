# AndroidUtils - ImagePicker
Quick utility to launch camera and capture image


## Usage

### Step 1: Import library

```agsl
implement io.github.geofriendtech:imageutils:1.0.0
```

### Step 2: Initialise ImagePicker in `Activity.onCreate()` or `Fragment.onCreate()` method

```agsl
final ImagePicker imagePicker = new ImagePicker(this);
```

### Step 2: Start ImagePicker to pick Image

```agsl
imagePicker.pickImage(new ImagePicker.ImageResultCallback() {
                    @Override
                    public void onImageResult(boolean success, Intent resultIntent, Uri imagePath) {
                        ImageView imageView = findViewById(R.id.iv_image);
                        imageView.setImageURI(imagePath);
                    }
                });
```

## Future Roadmap
Basic Image Editing Functionality