package in.geofriend.permissions;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

/**
 * This class responsible for seeking required app permissions : Camera, Location and Storage
 *
 * @author https://www.linkedin.com/in/vkvashistha/
 */
public class AppPermissionManager implements ActivityResultCallback<Map<String, Boolean>> {
    private final Context context;
    private final ActivityResultLauncher<String[]> requestPermissionLauncher;
    private AppPermissionCallback callback;
    public AppPermissionManager(Context context) {
        this.context = context;
        requestPermissionLauncher = ((AppCompatActivity) context).registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), this);
    }

    public boolean hasAllPermissions(String [] permissions) {
        boolean hasAllPermissions = true;
        for(int i=0; i<permissions.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(context.checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    hasAllPermissions = false;
                }
            }
        }
        return hasAllPermissions;
    }

    public void requestForPermissions(String [] permissions, AppPermissionCallback callback) {
        requestPermissionLauncher.launch(permissions);
        this.callback = callback;
    }

    @Override
    public void onActivityResult(Map<String, Boolean> result) {
        if(callback != null) {
            callback.onPermissionResult(result);
            callback = null;
        }
    }

    interface AppPermissionCallback {
        void onPermissionResult(Map<String, Boolean> result);
    }
}