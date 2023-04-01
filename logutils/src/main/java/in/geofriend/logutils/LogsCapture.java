package in.geofriend.logutils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LogsCapture {

    private static final String LOGCAT_CMD = "logcat -v long";

    private static Process mProcess;
    private static BufferedReader mReader;

    private static boolean mRunning;
    private static StringBuilder logs = new StringBuilder();
    private static final String processId = Integer.toString(android.os.Process
            .myPid());
    private static List<GLog> allLogs = new ArrayList<>();

    public static void start() {
        try {
            mProcess = Runtime.getRuntime().exec(LOGCAT_CMD);
            mReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()));
            mRunning = true;
            Pattern p = Pattern.compile("\\d{2}-\\d{2}");

            new Thread(() -> {
                String line;
                GLog gLog = null;
                StringBuilder log = new StringBuilder();
                try {
                    while (mRunning && (line = mReader.readLine()) != null) {
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
                            gLog.tag = logMetaTokens[logMetaTokens.length-1].split("/")[1];
                            gLog.logLevel = logMetaTokens[logMetaTokens.length-1].split("/")[0];
                            gLog.message = line.substring(line.indexOf("]") + 1);
                        } else {
                            log.append(line).append("\n");
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

    public static void stop() {

    }

    public static List<GLog> getLogs() {
        return allLogs;
    }
}
