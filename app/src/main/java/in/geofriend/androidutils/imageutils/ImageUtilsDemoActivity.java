package in.geofriend.androidutils.imageutils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import in.geofriend.androidutils.R;
import in.geofriend.imageutils.ImagePicker;

public class ImageUtilsDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_utils_demo);
        Button chooseImage = findViewById(R.id.btn_choose_image);
        ImagePicker imagePicker = new ImagePicker(this);
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker.pickImage(new ImagePicker.ImageResultCallback() {
                    @Override
                    public void onImageResult(boolean success, Intent resultIntent, Uri imagePath) {
                        ImageView imageView = findViewById(R.id.iv_image);
                        imageView.setImageURI(imagePath);
                    }
                });
            }
        });
    }
}