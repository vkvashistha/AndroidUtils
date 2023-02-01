package in.geofriend.androidutils.smsutils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import in.geofriend.androidutils.R;
import in.geofriend.permissions.AppPermissionManager;
import in.geofriend.smsutils.SMSUtils;
import in.geofriend.smsutils.Sms;

public class SMSDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsdemo);
        AppPermissionManager appPermissionManager = new AppPermissionManager(this);
        if(!appPermissionManager.hasAllPermissions(new String[] {Manifest.permission.READ_SMS})) {
            appPermissionManager.requestForPermissions(new String[]{Manifest.permission.READ_SMS}, null);
        }
        RecyclerView recyclerView = findViewById(R.id.sms_list);
        final SMSListAdapter adapter = new SMSListAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final SMSUtils smsUtils = new SMSUtils(this);
        adapter.submitList(smsUtils.readInboxSms());
        findViewById(R.id.fetch_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.submitList(smsUtils.readInboxSms());
            }
        });
    }
}