package in.geofriend.imageutils;
import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class ImagePicker implements ActivityResultCallback<ActivityResult> {
    private final ActivityResultLauncher<Intent> launcher;
    private Context context;
    private ImageResultCallback callback;
    public ImagePicker(Context context) {
        this.context = context;
        launcher = ((AppCompatActivity) context).registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this);
    }

    public void pickImage(Intent intent, ImageResultCallback callback) {
        this.callback = callback;
        launcher.launch(intent);
    }

    public void pickImage(ImageResultCallback callback) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImage(galleryIntent, callback);
    }

    @Override
    public void onActivityResult(ActivityResult result) {
        if(result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getData() != null) {
            if (callback != null) {
                callback.onImageResult(true, result.getData(), result.getData().getData());
            }
        } else {
            if(callback != null) {
                callback.onImageResult(false, result.getData(), null);
            }
        }
    }

    public interface ImageResultCallback {
        void onImageResult(boolean success, Intent resultIntent, Uri imagePath);
    }
}