package in.geofriend.logutils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class LogsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);
        Log.d("LogsActivity", "\n\n\nThis is a sample Log");
        RecyclerView recyclerView = findViewById(R.id.rv_logs);
        LogListAdapter adapter = new LogListAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.btn_export).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LogsActivity", "\n\n\nThis is a sample Log");
                adapter.submitList(LogsCapture.getLogs());
            }
        });
    }
}