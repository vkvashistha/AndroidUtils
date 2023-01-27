package in.geofriend.camerahelper;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

/**
 * Helper class to take photo using Camera app.
 *
 * @author https://www.linkedin.com/in/vkvashistha/
 */
public class CameraHelper implements ActivityResultCallback<ActivityResult> {
    private CameraResultCallback callback;
    private final ActivityResultLauncher<Intent> launcher;
    private final Context context;
    private Uri imagePath;

    public CameraHelper(Context context) {
        this.context = context;
        launcher = ((AppCompatActivity) context).registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this);
    }

    public void takePhoto(Intent cameraIntent, CameraResultCallback callback) {
        this.callback = callback;
        if(cameraIntent.resolveActivity(context.getPackageManager()) != null) {


            launcher.launch(cameraIntent);
        } else {
            if(callback != null) {
                callback.onCameraResult(false, null, null);
            }
        }
    }

    public Uri takePhoto(String filePath, CameraResultCallback callback) {
        return takePhoto(filePath, null, callback);
    }

    public Uri takePhoto(String filePath, Bundle additionalParams, CameraResultCallback callback) {
        try {
            File dirs = createMissingDirectories(filePath);
            imagePath = getImagePath(dirs);
            Intent cameraIntent = prepareIntent(imagePath);
            if(additionalParams != null) {
                cameraIntent.putExtras(additionalParams);
            }
            takePhoto(cameraIntent, callback);
        } catch (IOException e) {
            Log.e("Message","Message",e);

            callback.onCameraResult(false, null, null);
        }
        return imagePath;
    }

    private File createMissingDirectories(String filePath) throws IOException {
        int dirIndex = filePath.lastIndexOf(File.separator);
        File root = context.getFilesDir();

        File dir = new File(root, filePath.substring(0,dirIndex));
        dir.mkdirs();
        String imageFileName = filePath.substring(dirIndex+1);
        File imageFile = null;
        imageFile = new File(dir, imageFileName);
        imageFile.createNewFile();
        return imageFile;
    }

    private Uri getImagePath(File imageFile ) {

        return  FileProvider.getUriForFile(context.getApplicationContext(),
                context.getPackageName()+".provider",
                imageFile);

    }

    private Intent prepareIntent(Uri imagePath) {
        Intent cameraIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imagePath);
        return cameraIntent;
    }

    public Uri createImage(String filePath) {
        String relativePath = filePath.substring(0,filePath.lastIndexOf(File.pathSeparator));
        String imageName = filePath.substring(filePath.lastIndexOf(File.pathSeparator)+1);

        Uri uri;
        ContentResolver contentResolver = context.getContentResolver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, relativePath);
        Uri finalUri = contentResolver.insert(uri, contentValues);
        return finalUri;
    }

    @Override
    public void onActivityResult(ActivityResult result) {
        if(callback != null) {
            callback.onCameraResult(result.getResultCode() == RESULT_OK, result.getData(), imagePath);
        }
    }

    public interface CameraResultCallback {
        void onCameraResult(boolean success, Intent resultIntent, Uri imagePath);
    }
}
