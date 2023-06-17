package in.geofriend.logutils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class LogListAdapter extends ListAdapter<GLog, LogListAdapter.SmsViewHolder> {
    protected LogListAdapter() {
        super(new GLogDiffCallback());
    }

    @NonNull
    @Override
    public SmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_list_item, parent, false);
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

        public void bind(GLog log) {
            ((TextView) itemView.findViewById(R.id.loglevel)).setText(log.logLevel + "/" + log.tag);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm",Locale.getDefault());
            ((TextView) itemView.findViewById(R.id.time)).setText(log.timeStamp);
            ((TextView) itemView.findViewById(R.id.tv_desc)).setText(log.message);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("log", log.toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(v.getContext(), "Log Copied", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
