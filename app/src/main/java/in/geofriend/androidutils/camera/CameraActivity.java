package in.geofriend.androidutils.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import in.geofriend.androidutils.R;
import in.geofriend.camerahelper.CameraHelper;


public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        final CameraHelper cameraHelper = new CameraHelper(this);
        findViewById(R.id.take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraHelper.takePhoto("photo.jpg", new CameraHelper.CameraResultCallback() {
                    @Override
                    public void onCameraResult(boolean success, Intent resultIntent, Uri imagePath) {
                        ((ImageView)findViewById(R.id.camera_output)).setImageURI(imagePath);
                    }
                });
            }
        });
    }
}