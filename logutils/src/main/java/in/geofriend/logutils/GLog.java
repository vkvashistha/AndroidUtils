package in.geofriend.logutils;

import androidx.annotation.Nullable;

public class GLog {
    String timeStamp;
    String pkg;
    String message;
    String detailMessage;
    String tag;
    String logLevel;
    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "GLog{" +
                "timeStamp='" + timeStamp + '\'' +
                ", pkg='" + pkg + '\'' +
                ", message='" + message + '\'' +
                ", detailMessage='" + detailMessage + '\'' +
                ", tag='" + tag + '\'' +
                ", logLevel='" + logLevel + '\'' +
                '}';
    }
}
