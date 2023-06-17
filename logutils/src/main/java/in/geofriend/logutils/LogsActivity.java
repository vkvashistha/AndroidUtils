package in.geofriend.logutils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

public class LogsActivity extends AppCompatActivity {

    private LogListAdapter adapter;
    private RecyclerView logsRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);
        Log.d("LogsActivity", "\n\n\nThis is a sample Log");
        logsRecyclerView = findViewById(R.id.rv_logs);
        adapter = new LogListAdapter();
        logsRecyclerView.setAdapter(adapter);
        logsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void clearLogs() {
        adapter.submitList(new ArrayList<>());
        LogsCapture.clearLog();
    }

    private void scrollToLast() {
        int count = adapter.getItemCount();
        logsRecyclerView.scrollToPosition(count - 1);
    }

    private void syncLogs() {
        adapter.submitList(LogsCapture.getLogs());
    }
    private void scrollToFirst() {
        logsRecyclerView.scrollToPosition(0);
    }

    private void sendLogs() {
        LogsCapture.stop();
        LogsCapture.clearLog();
        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        File fileWithinMyDir = LogsCapture.getLogFile();

        if(fileWithinMyDir.exists()) {
            intentShareFile.setType("application/text");
            intentShareFile.putExtra(Intent.EXTRA_STREAM, getUri(this, fileWithinMyDir));

            intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
                    "Sharing File...");
            intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");

            startActivity(Intent.createChooser(intentShareFile, "Share File"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogsCapture.start(this);
    }

    public static Uri getUri(Context context, File file) {
        return FileProvider.getUriForFile(context.getApplicationContext(),
                context.getPackageName() + ".provider",
                file);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.clear_logs) {
            clearLogs();
        } else if (itemId == R.id.scroll_to_last) {
            scrollToLast();
        } else if (itemId == R.id.send_logs) {
            sendLogs();
        } else if (itemId == R.id.scroll_to_top) {
            scrollToFirst();
        } else if (itemId == R.id.sync_logs) {
            syncLogs();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.in_geofriend_logcat_action_menu, menu);
        return true;
    }
}