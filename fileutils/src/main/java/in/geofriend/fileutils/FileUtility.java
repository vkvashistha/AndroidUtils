//package in.geofriend.fileutility;
//
//import android.app.Activity;
//import android.content.ContentResolver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.net.Uri;
//import android.os.Environment;
//import android.os.ParcelFileDescriptor;
//import android.util.Log;
//
//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.documentfile.provider.DocumentFile;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.Objects;
//
//public class FileUtility implements ActivityResultCallback<ActivityResult> {
//    private File rootDirectory;
//    private Context context;
//    private ActivityResultLauncher<Intent> launcher;
//    private Uri rootDirectoryUri;
//
//    public FileUtility(Context context, File rootDirectory) {
//        this.rootDirectory = rootDirectory;
//        this.context = context;
//
//    }
//
//    public FileUtility(Context context) {
//        this.context = context;
//        launcher = ((AppCompatActivity) context).registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this);
//        SharedPreferences preferences = context.getSharedPreferences("in.geofriend.fileutility", Context.MODE_PRIVATE);
//        String uriString = preferences.getString("filestorageuri", "");
//        if (!uriString.isEmpty()) {
//            rootDirectoryUri = Uri.parse(uriString);
//            rootDirectory = new File(rootDirectoryUri.getPath());
//        } else {
//            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
//            launcher.launch(intent);
//        }
//    }
//
//    public void writeIntoFile(String filePath, String content, boolean append) throws IOException {
//        FileOutputStream fos = createFile(filePath, append);
//        fos.write(content.getBytes());
//        fos.close();
//    }
//
//    public String readFile(String filePath) throws IOException {
//        if(rootDirectoryUri != null) {
//            return readFileUsingSAF(filePath);
//        }
//        File file = new File(rootDirectory, filePath);
//        FileInputStream fis = new FileInputStream(file);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
//        StringBuilder builder = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            builder.append(line);
//        }
//        fis.close();
//        return builder.toString();
//    }
//
//    private String readFileUsingSAF(String filePath) throws IOException {
//        // Open a specific media item using InputStream.
//        ContentResolver resolver = context.getContentResolver();
//        DocumentFile rootDirectory = DocumentFile.fromTreeUri(context, rootDirectoryUri);
//        Uri fileUri = rootDirectoryUri.buildUpon().appendEncodedPath(filePath).build();
////        Uri fileUri = Uri.parse("content://com.android.externalstorage.documents/tree/primary%3ANOAA/document/primary%3ANOAA%2FTrip%2Ffile_1.csv");
//        try (InputStream stream = resolver.openInputStream(fileUri)) {
//            // Perform operations on "stream".
//            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//            StringBuilder builder = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                builder.append(line);
//            }
//            stream.close();
//            return builder.toString();
//        }
//    }
//
//    @SuppressWarnings("ResultOfMethodCallIgnored")
//    public FileOutputStream createFile(String filePath, boolean appendMode) throws IOException {
//        if(rootDirectoryUri != null) {
//            return createFileUsingSAF(filePath);
//        }
//        int dirIndex = filePath.lastIndexOf(File.separator);
//        File dir = rootDirectory;
//        if (dirIndex > 0) {
//            dir = new File(rootDirectory, filePath.substring(0, dirIndex));
//            dir.mkdirs();
//        }
//        String fileName = filePath.substring(dirIndex + 1);
//        File file = null;
//        file = new File(dir, fileName);
//        file.createNewFile();
//        return new FileOutputStream(file, appendMode);
//    }
//
//    public void writeIntoFile(Context context, String fileName, String content) {
////        File appSpecificExternalStorageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
//        File appSpecificInternalStorageDirectory = context.getFilesDir();
//        File file = new File(appSpecificInternalStorageDirectory, fileName);
//        file.createNewFile();
//        FileOutputStream fos = new FileOutputStream(file, false);
//        fos.write(content.getBytes());
//        fos.close();
//    }
//
//    public String readFromFile(String filePath) throws IOException {
//        //        File appSpecificExternalStorageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
//        File appSpecificInternalStorageDirectory = context.getFilesDir();
//        File file = new File(appSpecificInternalStorageDirectory, filePath);
//        FileInputStream fis = new FileInputStream(file);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
//        StringBuilder builder = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            builder.append(line);
//        }
//        fis.close();
//        return builder.toString();
//    }
//
//    private FileOutputStream createFileUsingSAF(String filePath) throws FileNotFoundException {
//        DocumentFile rootDirectory = DocumentFile.fromTreeUri(context, rootDirectoryUri);
//        int dirIndex = filePath.lastIndexOf(File.separator);
//        String fileName = filePath;
//        DocumentFile fileParentDirectory = rootDirectory;
//        if (dirIndex > 0) {
//            fileName = filePath.substring(dirIndex + 1);
//            String[] dirs = filePath.substring(0, dirIndex).split("/");
//            for (String dir : dirs) {
//                DocumentFile existingDirectory = null;
//                for (DocumentFile file : Objects.requireNonNull(fileParentDirectory).listFiles()) {
//                    if (file.isDirectory() && Objects.equals(file.getName(), dir)) {
//                        existingDirectory = file;
//                        break;
//                    }
//                }
//                if (existingDirectory == null) {
//                    fileParentDirectory = fileParentDirectory.createDirectory(dir);
//                } else {
//                    fileParentDirectory = existingDirectory;
//                }
//            }
//        }
//        DocumentFile file = Objects.requireNonNull(fileParentDirectory).createFile("text/csv", fileName);
//        ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(Objects.requireNonNull(file).getUri(), "w");
//        return new FileOutputStream(pfd.getFileDescriptor());
//    }
//
//    @Override
//    public void onActivityResult(ActivityResult result) {
//        if (result.getResultCode() == Activity.RESULT_OK) {
//            rootDirectoryUri = Objects.requireNonNull(result.getData()).getData();
//            final int takeFlags = (Intent.FLAG_GRANT_READ_URI_PERMISSION
//                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//// Check for the freshest data.
//            context.getContentResolver().takePersistableUriPermission(result.getData().getData(), takeFlags);
//            SharedPreferences preferences = context.getSharedPreferences("in.geofriend.fileutility", Context.MODE_PRIVATE);
//            preferences.edit().putString("filestorageuri", result.getData().getData().toString()).apply();
//            rootDirectory = new File(rootDirectoryUri.getPath());
//        } else {
//            Log.e("FileUtility", "Some Error Occurred : " + result);
//        }
//    }
//}
