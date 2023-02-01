package in.geofriend.androidutils.smsutils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import in.geofriend.androidutils.R;
import in.geofriend.smsutils.Sms;

public class SMSListAdapter extends ListAdapter<Sms, SMSListAdapter.SmsViewHolder> {
    protected SMSListAdapter() {
        super(new SmsDiffCallback());
    }

    @NonNull
    @Override
    public SmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sms_list_item, parent, false);
        return new SmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class SmsViewHolder extends RecyclerView.ViewHolder {

        public SmsViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Sms sms) {
            ((TextView) itemView.findViewById(R.id.address)).setText(sms.getAddress());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm",Locale.getDefault());
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(Long.parseLong(sms.getTime()));
            String formattedtime = sdf.format(c.getTime());
            ((TextView) itemView.findViewById(R.id.time)).setText(formattedtime);
            ((TextView) itemView.findViewById(R.id.sms_text)).setText(sms.getMsg());
        }
    }
}
