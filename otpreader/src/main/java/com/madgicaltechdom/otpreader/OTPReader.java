package com.madgicaltechdom.otpreader;

import android.content.Context;
import android.content.IntentFilter;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class OTPReader {
    private Context context;
    SmsRetrieverClient client;

    public OTPReader(Context context) {
        this.context = context;
        client = SmsRetriever.getClient(context);
    }

    public void retrieveOTP(OTPReaderCallback callback) {
        // Starts SmsRetriever, which waits for ONE matching SMS message until timeout
        // (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
        // action SmsRetriever#SMS_RETRIEVED_ACTION.
        Task<Void> task = client.startSmsRetriever();

        // Listen for success/failure of the start Task. If in a background thread, this
        // can be made blocking using Tasks.await(task, [timeout]);
        task.addOnSuccessListener(aVoid -> {
            context.registerReceiver(new SMSBroadcastReciever(callback), new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION),SmsRetriever.SEND_PERMISSION, null);
        });

        task.addOnFailureListener(e -> callback.onOTPReceived(false, "Unable to initialize SMS retriever"));
    }

    public interface OTPReaderCallback {
        void onOTPReceived(boolean isSuccess, String message);
    }
}
