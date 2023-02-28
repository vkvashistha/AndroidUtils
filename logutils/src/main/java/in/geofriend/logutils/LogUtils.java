package in.geofriend.logutils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class LogUtils {
    private static final String TAG = LogUtils.class.getCanonicalName();
    private static final String processId = Integer.toString(android.os.Process
            .myPid());
    public static List<GLog> fetchLogs() {
        ArrayList<GLog> allLogs = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec("logcat -d");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            GLog gLog = null;
            StringBuilder log = new StringBuilder();
            String line;
            Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
            while ((line = bufferedReader.readLine()) != null) {
                String prefix = line.substring(0, 10);
                if (p.matcher(prefix).matches()) {
                    if (gLog != null) {
                        gLog.detailMessage = log.toString();
                        allLogs.add(gLog);
                    }
                    log = new StringBuilder();
                    gLog = new GLog();
                    gLog.timeStamp = line.substring(0, 32);
                    int pkgStartPos = line.indexOf('/') + 1;
                    int pkgEndPos = line.indexOf(' ', pkgStartPos);
                    String pkgName = line.substring(pkgStartPos, pkgEndPos);
                    int logLevelEndIndex = line.indexOf(':', pkgEndPos);
                    String tagLogLevel = line.substring(pkgEndPos, logLevelEndIndex).trim();
                    String logLevel = tagLogLevel.split("/")[0];
                    String tag = tagLogLevel.split("/")[1];
                    gLog.pkg = pkgName.trim();
                    gLog.tag = tag;
                    gLog.logLevel = logLevel;
                    gLog.message = tagLogLevel.substring(logLevelEndIndex);

                } else {
                    log.append(line);
                }
            }
            gLog = new GLog();
            gLog.message = log.toString();
            gLog.timeStamp = new SimpleDateFormat( "dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
            allLogs.add(gLog);
        } catch (IOException e) {
            Log.e("LogUtils", "Unable to fetch logcat logs",e);
        }
        return allLogs;
    }

    public static void clearLog(){
        try {
            new ProcessBuilder()
                    .command("logcat", "-c")
                    .redirectErrorStream(true)
                    .start();
        } catch (IOException e) {
            Log.e("LogUtils", "Some error occurred while clearing the logs", e);
        }
    }

    public static List<GLog> getLog() {
        ArrayList<GLog> allLogs = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        try {
            String[] command = new String[] { "logcat", "-d", "-v", "threadtime" };

            Process process = Runtime.getRuntime().exec(command);

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(processId)) {
                    builder.append(line);
                    //Code here
                }
            }
        } catch (IOException ex) {
            Log.e(TAG, "getLog failed", ex);
        }
        GLog gLog = new GLog();
        gLog.message = builder.toString();
        allLogs.add(gLog);
        return allLogs;
    }
}
