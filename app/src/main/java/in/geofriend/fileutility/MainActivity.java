package in.geofriend.fileutility;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private int fileNumber = 0;
    SAFFileManager fileManager;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final StorageAccessManager storageAccessManager = new StorageAccessManager(this);
        storageAccessManager.openDestinationChooser(new StorageAccessManager.DestinationSelectionListener() {
            @Override
            public void onDestinationSelected(int status, Uri destination) {
                fileManager = new SAFFileManager(MainActivity.this, destination);
            }
        });
        Uri  contentUri = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);

        findViewById(R.id.button).setOnClickListener(l -> {
            try {
                fileManager.writeFile("Trip/hello.csv", "This is a text file\n".getBytes(StandardCharsets.UTF_8), true);
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, "Some error occurred while writing content to file " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.button2).setOnClickListener(l -> {
            try {
                ((TextView)findViewById(R.id.textView)).setText(fileManager.readFileAsString("Trip/hello.csv"));
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, "Some error occurred while reading content from file " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.button3).setOnClickListener(l -> {
            try {
                storageAccessManager.openDestinationChooser(new StorageAccessManager.DestinationSelectionListener() {
                    @Override
                    public void onDestinationSelected(int status, Uri destination) {
                        fileManager = new SAFFileManager(MainActivity.this, destination);
                    }
                });
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Some error occurred while reading content from file " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (requestCode == 1
                && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;
            if (resultData != null) {
                FileUtility fileUtility = new FileUtility(this, getFilesDir());
                uri = resultData.getData();
                DocumentFile pickedDir = DocumentFile.fromTreeUri(this, uri);
                DocumentFile noaaDir = pickedDir.createDirectory("NOAA");
                DocumentFile file = noaaDir.createFile("Application/text", "file_"+fileNumber+".csv");
                ParcelFileDescriptor pfd = null;
                try {
                    pfd = getContentResolver().
                            openFileDescriptor(file.getUri(), "w");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                FileOutputStream fileOutputStream =
                        new FileOutputStream(pfd.getFileDescriptor());
                try {
                    fileOutputStream.write(("Overwritten at " + System.currentTimeMillis() +
                            "\n").getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Let the document provider know you're done by closing the stream.
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    pfd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, "URI : " + uri, Toast.LENGTH_LONG).show();
                // Perform operations on the document using its URI.
            }
        }
    }*/
}