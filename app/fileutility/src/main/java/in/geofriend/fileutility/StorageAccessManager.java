package in.geofriend.fileutility;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class StorageAccessManager implements ActivityResultCallback<ActivityResult> {
    private Context context;
    private ActivityResultLauncher<Intent> launcher;
    private DestinationSelectionListener listener;
    public StorageAccessManager(Context context) {
        this.context = context;
        launcher = ((AppCompatActivity) context).registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this);
    }
    public void openDestinationChooser(DestinationSelectionListener listener) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        launcher.launch(intent);
        this.listener = listener;

    }

    @Override
    public void onActivityResult(ActivityResult result) {
        listener.onDestinationSelected(DestinationSelectionListener.STATUS_OK, result.getData().getData());
    }

    public interface DestinationSelectionListener {
        int STATUS_OK = 0;
        void onDestinationSelected(int status, Uri destination);
    }
}
