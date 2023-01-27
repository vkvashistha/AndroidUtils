package in.geofriend.androidutils.fileutility;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import in.geofriend.androidutils.R;
import in.geofriend.fileutils.SAFFileManager;
import in.geofriend.fileutils.StorageAccessManager;


public class FileUtilityActivity extends AppCompatActivity {
    private int fileNumber = 0;
    SAFFileManager fileManager;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fileutility);
        final StorageAccessManager storageAccessManager = new StorageAccessManager(this);
        findViewById(R.id.write_into_file).setOnClickListener(l -> {
            try {
                String filePath = ((TextView)findViewById(R.id.file_path)).getText().toString();
                String content = ((TextView)findViewById(R.id.file_content)).getText().toString();
                if(fileManager == null) {
                    storageAccessManager.openDestinationChooser(new StorageAccessManager.DestinationSelectionListener() {
                        @Override
                        public void onDestinationSelected(int status, Uri destination) {
                            fileManager = new SAFFileManager(FileUtilityActivity.this, destination);
                            try {
                                fileManager.writeFile(filePath, content.getBytes(StandardCharsets.UTF_8), false);
                                Toast.makeText(FileUtilityActivity.this, "File Saved Successfully", Toast.LENGTH_SHORT).show();
                            }catch (IOException e) {
                                Log.e("FileUtilty", "Some error occurred while writing content to file ",e);
                                Toast.makeText(FileUtilityActivity.this, "Some error occurred while writing content to file " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    fileManager.writeFile(filePath, content.getBytes(StandardCharsets.UTF_8), false);
                }
            } catch (IOException e) {
                Toast.makeText(FileUtilityActivity.this, "Some error occurred while writing content to file " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.read_file).setOnClickListener(l -> {
            try {
                String filePath = ((TextView)findViewById(R.id.file_path)).getText().toString();
                ((TextView)findViewById(R.id.file_content)).setText(fileManager.readFileAsString(filePath));
            } catch (IOException e) {
                Toast.makeText(FileUtilityActivity.this, "Some error occurred while reading content from file " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.choose_base_directory).setOnClickListener(l -> {
            try {
                storageAccessManager.openDestinationChooser(new StorageAccessManager.DestinationSelectionListener() {
                    @Override
                    public void onDestinationSelected(int status, Uri destination) {
                        fileManager = new SAFFileManager(FileUtilityActivity.this, destination);
                    }
                });
            } catch (Exception e) {
                Toast.makeText(FileUtilityActivity.this, "Some error occurred while reading content from file " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }
}