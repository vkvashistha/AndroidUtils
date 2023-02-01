package in.geofriend.smsutils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class SMSUtils {
    private Context context;

    public SMSUtils(Context context) {
        this.context = context;
    }

    // public static final String INBOX = "content://sms/inbox";
    private List<Sms> readSms(Uri uri) {
        ArrayList<Sms> sms = new ArrayList<>();
        try (Cursor c = context.getContentResolver().query(uri, null, null, null, null)) {
            if (c.moveToFirst()) { // must check the result to prevent exception
                do {
                    Sms objSms = new Sms();
                    objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                    objSms.setAddress(c.getString(c
                            .getColumnIndexOrThrow("address")));
                    objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                    objSms.setReadState(c.getString(c.getColumnIndexOrThrow("read")));
                    objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                    if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                        objSms.setFolderName("inbox");
                    } else {
                        objSms.setFolderName("sent");
                    }
                    sms.add(objSms);
                    // use msgData
                } while (c.moveToNext());
            }
        }
        return sms;
    }

    public List<Sms> readAllSms() {
        return readSms(Uri.parse("content://sms"));
    }

    public List<Sms> readInboxSms() {
        return readSms(Uri.parse("content://sms/inbox"));
    }
    // public static final String SENT = "content://sms/sent";
    public List<Sms> readAllSentSms() {
        return readSms(Uri.parse("content://sms/sent"));
    }

    // public static final String DRAFT = "content://sms/draft";
    public List<Sms> readAllDraftSms() {
        return readSms(Uri.parse("content://sms/draft"));
    }
}
