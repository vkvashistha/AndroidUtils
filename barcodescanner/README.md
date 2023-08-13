# AndroidUtils - Barcode Scanner
Quick Utility to scan barcode


## Usage

### Step 1: Import library

```agsl
implement io.github.geofriendtech:barcodescanner:1.0.0
```

### Step 2: Initialise BarcodeScanner in `Activity.onCreate()` or `Fragment.onCreate()` method

```agsl
BarcodeScanner scanner = new BarcodeScanner(this);
```

### Step 3: Start Scanner to scan Barcode

```agsl
scanner.scan((success, result) -> {
                if(success) {
                    ((TextView) findViewById(R.id.tv_scanned_code)).setText(result);
                } else {
                    ((TextView) findViewById(R.id.tv_scanned_code)).setText("Error");
                }
            });
```


## Future Roadmap
Add support for QR Code with various formats