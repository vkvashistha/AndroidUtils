package in.geofriend.fileutility;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {
    protected Context context;

    public FileManager(Context context) {
        this.context = context;
    }

    private File createFile(String filePath) throws IOException {
        int dirIndex = filePath.lastIndexOf(File.separator);
        File rootDirectory = context.getFilesDir();
        File dir = rootDirectory;
        if (dirIndex > 0) {
            dir = new File(rootDirectory, filePath.substring(0, dirIndex));
            dir.mkdirs();
        }
        String fileName = filePath.substring(dirIndex + 1);
        File file = null;
        file = new File(dir, fileName);
        if(!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public String readFileAsString(String filePath) throws IOException {
        return null;
    }

    public boolean writeFile(String filePath, byte[] data, boolean toAppend) throws IOException {
        File file = createFile(filePath);
        if(file == null || !file.exists()) {
            return false;
        }
        FileOutputStream fos = new FileOutputStream(createFile(filePath), false);
        fos.write(data);
        fos.close();
        return true;
    }

    public boolean copyFile(String fromPath, String toPath) {
        return false;
    }

    public boolean moveFile(String fromPath, String toPath) {
        return false;
    }

    public File makeDirs(String filePath) {
        File rootDirectory = context.getFilesDir();
        File dir = new File(rootDirectory, filePath);
        dir.mkdirs();
        return dir;
    }
}
