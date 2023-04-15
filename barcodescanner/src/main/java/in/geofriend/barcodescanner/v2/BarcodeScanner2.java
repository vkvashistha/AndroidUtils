package in.geofriend.barcodescanner.v2;

import android.content.Context;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import com.google.android.gms.common.api.OptionalModuleApi;
import com.google.android.gms.common.moduleinstall.ModuleInstall;
import com.google.android.gms.common.moduleinstall.ModuleInstallClient;
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest;
import com.google.android.gms.tflite.java.TfLite;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

public class BarcodeScanner2 implements ActivityResultCallback<ActivityResult> {
    GmsBarcodeScanner scanner;
    private Context context;

    public BarcodeScanner2(Context context) {
        scanner = GmsBarcodeScanning.getClient(context);
        this.context = context;
    }

    public void scan(in.geofriend.barcodescanner.BarcodeScanner.BarcodeScanResultCallback listener) {
        checkCodeScannerModuleAvailability(listener);

    }

    private void startScanning(in.geofriend.barcodescanner.BarcodeScanner.BarcodeScanResultCallback listener) {
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

    private void checkCodeScannerModuleAvailability(in.geofriend.barcodescanner.BarcodeScanner.BarcodeScanResultCallback listener) {
        ModuleInstallClient moduleInstallClient = ModuleInstall.getClient(context);
        OptionalModuleApi optionalModuleApi = TfLite.getClient(context);
        moduleInstallClient.releaseModules(optionalModuleApi);
        if(true) return;
        moduleInstallClient
                .areModulesAvailable(optionalModuleApi)
                .addOnSuccessListener(
                        response -> {
                            if (response.areModulesAvailable()) {
                                // Modules are present on the device...
                                startScanning(listener);
                            } else {
                                // Modules are not present on the device...
                                installModule(listener);
                            }
                        })
                .addOnFailureListener(
                        e -> {
                            // Handle failure…
                            listener.onBarcodeScanCompleted(false, null);
                        });
    }

    private void installModule(in.geofriend.barcodescanner.BarcodeScanner.BarcodeScanResultCallback listener) {
        OptionalModuleApi optionalModuleApi = TfLite.getClient(context);
        ModuleInstallClient moduleInstallClient = ModuleInstall.getClient(context);
        ModuleInstallRequest moduleInstallRequest =
                ModuleInstallRequest.newBuilder()
                        .addApi(optionalModuleApi)
                        // Add more API if you would like to request multiple optional modules
                        //.addApi(...)
                        // Set the listener if you need to monitor the download progress
                        //.setListener(listener)
                        .build();
        moduleInstallClient.installModules(moduleInstallRequest)
                .addOnSuccessListener(
                        response -> {
                            if (response.areModulesAlreadyInstalled()) {
                                // Modules are already installed when the request is sent.
                            }
                            startScanning(listener);
                        })
                .addOnFailureListener(
                        e -> {
                            // Handle failure...
                        });
    }
    @Override
    public void onActivityResult(ActivityResult result) {

    }

    public interface BarcodeScanResultCallback {
        void onBarcodeScanCompleted(boolean success, String result);
    }
}