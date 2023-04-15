package in.geofriend.barcodescanner.v2;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class BarcodeScanner implements ActivityResultCallback<ActivityResult>  {
    private ActivityResultLauncher<Intent> launcher;
    private Context context;
    private BarcodeScanResultCallback callback;

    public BarcodeScanner(Context context) {
        this.context = context;
        launcher = ((AppCompatActivity) context).registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this);
    }

    public void scan(BarcodeScanResultCallback listener) {
        Activity currentActivity = (Activity) context;
        Intent intent = new Intent(currentActivity, BarcodeScannerActivity.class);
        launcher.launch(intent);
        this.callback = listener;
    }

    @Override
    public void onActivityResult(ActivityResult result) {
        if(callback != null) {
            if(result.getData() != null) {
                callback.onBarcodeScanCompleted(result.getResultCode() == RESULT_OK, result.getData().getExtras().getString("content"));
            } else {
                callback.onBarcodeScanCompleted(false, null);
            }
        }
    }

    public interface BarcodeScanResultCallback {
        void onBarcodeScanCompleted(boolean success, String result);
    }
}
