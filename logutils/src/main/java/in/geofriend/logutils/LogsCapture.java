package in.geofriend.logutils;


import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class LogsCapture {

    private static final String LOGCAT_CMD = "logcat -v long";
    private static final String LOGCAT_CLEAR_CMD = "logcat -v long";

    private static Process mProcess;
    private static BufferedReader mReader;

    private static boolean mRunning;
    private static StringBuilder logs = new StringBuilder();
    private static final String processId = Integer.toString(android.os.Process
            .myPid());
    private static List<GLog> allLogs = new ArrayList<>();
    private static  FileOutputStream logOutputFileStream;
    private static final String LOG_FILE_NAME = new SimpleDateFormat("yyyyMMdd", Locale.ROOT).format(Calendar.getInstance().getTime()) + ".log";
    private static File logsFile;
    private static final Object lock = new Object();

    public static void start(Context context) {
        if(mRunning) {
            return;
        }
        try {
            mProcess = Runtime.getRuntime().exec(LOGCAT_CMD);
            mReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()));
            mRunning = true;
            Pattern p = Pattern.compile("\\d{2}-\\d{2}");

            File dir = new File(context.getFilesDir(), "in_geofriend_androidutils_logs");
            if(!dir.exists()) {
                dir.mkdirs();
            }
            logsFile = new File(dir, LOG_FILE_NAME);

            if(!logsFile.exists()) {
                if(!logsFile.createNewFile()) {
                    return;
                }
            }
            logOutputFileStream = new FileOutputStream(logsFile, true);

            new Thread(() -> {
                String line;
                GLog gLog = null;
                StringBuilder log = new StringBuilder();
                try {
                    while (mRunning && (line = mReader.readLine()) != null) {
                        synchronized (lock) {
                            logOutputFileStream.write(line.getBytes(StandardCharsets.UTF_8));
                            logOutputFileStream.write("\n".getBytes(StandardCharsets.UTF_8));
                        }
                        try {
                            String prefix = "";
                            String[] logMetaTokens = null;
                            if (line.startsWith("[")) {
                                String logMeta = line.substring(1, line.indexOf("]")).trim();
                                logMetaTokens = logMeta.split(" ");
                                prefix = logMetaTokens[0];
                            }
                            if (p.matcher(prefix).matches()) {
                                if (gLog != null) {
                                    gLog.message = log.toString();
                                    allLogs.add(gLog);
                                }
                                log = new StringBuilder();
                                gLog = new GLog();
                                String[] tokens = line.split(" ", 4);
                                gLog.timeStamp = logMetaTokens[0] + " " + logMetaTokens[1];
                                String tagToken = logMetaTokens[logMetaTokens.length-1];
                                if(!logMetaTokens[logMetaTokens.length-1].contains("/")) {
                                    tagToken = logMetaTokens[logMetaTokens.length-2];
                                }
                                if(tagToken.contains("/")) {
                                    gLog.tag = tagToken.split("/")[1];
                                    gLog.logLevel = tagToken.split("/")[0];
                                }
                                gLog.message = line.substring(line.indexOf("]") + 1);
                            } else {
                                log.append(line).append("\n");
                            }
                        } catch (Exception e) {
                            Log.e("LogCapture", "Some error occured while capturing log line: " + line, e);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    stop();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  void stop()  {
        try {
            synchronized (lock) {
                mRunning = false;
                if(logOutputFileStream != null) {
                    logOutputFileStream.flush();
                    logOutputFileStream.close();
                }
            }

        } catch (IOException ignore) {
        }

    }

    public static File getLogFile() {
        return logsFile;
    }

    public static void clearLog(){
        try {
            allLogs.clear();
            Process process = new ProcessBuilder()
                    .command("logcat", "-c")
                    .redirectErrorStream(true)
                    .start();
        } catch (IOException e) {
        }
    }
    public static List<GLog> getLogs() {
        return allLogs;
    }
}
