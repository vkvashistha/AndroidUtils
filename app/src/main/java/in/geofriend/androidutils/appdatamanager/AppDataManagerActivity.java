package in.geofriend.androidutils.appdatamanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import in.geofriend.androidutils.R;
import in.geofriend.appdatamanager.AppDataManager;
import in.geofriend.appdatamanager.AppStorage;

public class AppDataManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_data_manager);
        EditText enterKey = findViewById(R.id.enter_key);
        EditText enterValue = findViewById(R.id.enter_value);
        EditText enterGetKey = findViewById(R.id.et_key);
        TextView getValue = findViewById(R.id.tv_value);
        Button saveInTemp = findViewById(R.id.btn_save_temp);
        Button saveInSharedPref = findViewById(R.id.btn_save_shared_pref);
        Button saveInSQLite = findViewById(R.id.btn_save_data);


        Button fetchFromTemp = findViewById(R.id.btn_get_value_temp);
        Button fetchFromSqlite = findViewById(R.id.btn_get_value_sqlite);
        Button fetchFromSharedPref = findViewById(R.id.btn_get_value_shared_pref);

        saveInTemp.setOnClickListener(v-> {
            String key = enterKey.getText().toString();
            String val = enterValue.getText().toString();
            AppDataManager.getStorage(AppDataManager.STORAGE_TYPE_TEMPORARY).put(key, val);
        });

        saveInSharedPref.setOnClickListener(v-> {
            String key = enterKey.getText().toString();
            String val = enterValue.getText().toString();
            AppDataManager.getStorage(AppDataManager.STORAGE_TYPE_SHARED_PREFERENCE).withContext(this).put(key, val);
        });

        saveInSQLite.setOnClickListener(v-> {
            String key = enterKey.getText().toString();
            String val = enterValue.getText().toString();
            AppDataManager.getStorage(AppDataManager.STORAGE_TYPE_DATABASE).withContext(this).put(key, val);
        });

        fetchFromTemp.setOnClickListener(v -> {
            String key = enterGetKey.getText().toString();
            AppStorage.AppData data = AppDataManager.getStorage(AppDataManager.STORAGE_TYPE_TEMPORARY).get(key);
            if (data != null) {
                String value = data.asString();
                getValue.setText("Temp:"+value);
            }
        });

        fetchFromSqlite.setOnClickListener(v -> {
            String key = enterGetKey.getText().toString();
            AppStorage.AppData data = AppDataManager.getStorage(AppDataManager.STORAGE_TYPE_DATABASE).withContext(this).get(key);
            if (data != null) {
                String value = data.asString();
                getValue.setText("SQLite:"+value);
            }
        });

        fetchFromSharedPref.setOnClickListener(v -> {
            String key = enterGetKey.getText().toString();
            AppStorage.AppData data = AppDataManager.getStorage(AppDataManager.STORAGE_TYPE_SHARED_PREFERENCE).withContext(this).get(key);
            if (data != null) {
                String value = data.asString();
                getValue.setText("SharedPref:"+value);
            }
        });
    }
}