package in.geofriend.androidutils.smsutils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import in.geofriend.smsutils.Sms;

public class SmsDiffCallback extends DiffUtil.ItemCallback<Sms> {
    @Override
    public boolean areItemsTheSame(@NonNull Sms oldItem, @NonNull Sms newItem) {
        return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Sms oldItem, @NonNull Sms newItem) {
        return oldItem.getId().equals(newItem.getId());
    }
}
