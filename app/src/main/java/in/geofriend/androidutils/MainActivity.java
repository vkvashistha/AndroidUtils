package in.geofriend.androidutils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.geofriend.androidutils.camera.CameraActivity;
import in.geofriend.androidutils.fileutility.FileUtilityActivity;
import in.geofriend.androidutils.imageutils.ImageUtilsDemoActivity;
import in.geofriend.androidutils.location.LocationActivity;
import in.geofriend.androidutils.smsutils.SMSDemoActivity;
import in.geofriend.camerahelper.CameraHelper;
import in.geofriend.locationhelper.LocationHelper;
import in.geofriend.logutils.LogsActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayoutCompat listContainer = findViewById(R.id.listContainer);
        for(String item : Constants.DEMOS) {
            ViewGroup layout = (ViewGroup) getLayoutInflater().inflate(R.layout.demo_list_item, null);
            TextView textView = layout.findViewById(R.id.demo_name);
            textView.setText(item);
            listContainer.addView(layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicked(item);
                }
            });
        }
    }

    private void onItemClicked(String item) {
        Intent intent = null;
        switch (item) {
            case Constants.FILE_UTILITY:
                intent = new Intent(this, FileUtilityActivity.class);
                break;
            case Constants.CAMERA_HELPER:
                intent = new Intent(this, CameraActivity.class);
                break;
            case Constants.LOCATION_HELPER:
                intent = new Intent(this, LocationActivity.class);
                break;
            case Constants.SMS_UTILS:
                intent = new Intent(this, SMSDemoActivity.class);
                break;
            case Constants.IMAGE_PICKER:
                intent = new Intent(this, ImageUtilsDemoActivity.class);
                break;
            case Constants.LOGCAT_LOGS:
                intent = new Intent(this, LogsActivity.class);
        }
        startActivity(intent);
    }
}