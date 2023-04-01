package in.geofriend.logutils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class GLogDiffCallback extends DiffUtil.ItemCallback<GLog> {
    @Override
    public boolean areItemsTheSame(@NonNull GLog oldItem, @NonNull GLog newItem) {
        return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull GLog oldItem, @NonNull GLog newItem) {
        return oldItem.equals(newItem);
    }
}
