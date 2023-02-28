package in.geofriend.barcodescanner;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

public class BarcodeScanner implements ActivityResultCallback<ActivityResult> {
    GmsBarcodeScanner scanner;

    public BarcodeScanner(Context context) {
        scanner = GmsBarcodeScanning.getClient(context);
    }

    public void scan(BarcodeScanResultCallback listener) {
        scanner.startScan().addOnSuccessListener(
                        barcode -> {
                            listener.onBarcodeScanCompleted(true,  barcode.getDisplayValue());
                            // Task completed successfully
                        })
                .addOnCanceledListener(
                        () -> {
                            // Task canceled
                        })
                .addOnFailureListener(
                        e -> {
                            // Task failed with an exception
                            listener.onBarcodeScanCompleted(false,  null);
                        });

    }

    @Override
    public void onActivityResult(ActivityResult result) {

    }

    public interface BarcodeScanResultCallback {
        void onBarcodeScanCompleted(boolean success, String result);
    }
}
