package in.geofriend.appdatamanager;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.WeakReference;

public class SharedPreferenceStorage extends AppStorage{
    private WeakReference<Context> contextWeakReference;

    @Override
    public SharedPreferenceStorage withContext(Context context) {
        contextWeakReference = new WeakReference<>(context);
        return this;
    }

    @Override
    public SharedPreferenceStorage put(String key, Object value) {
        if(contextWeakReference == null || contextWeakReference.get() != null) {
            Context context = contextWeakReference.get();
            SharedPreferences.Editor values = context.getSharedPreferences(getClass().getName(), Context.MODE_PRIVATE).edit();
            if(value instanceof String) {
                values.putString(key, value.toString());
            } else if(value instanceof Integer) {
                values.putInt(key, (Integer) value);
            } else if(value instanceof Float) {
                values.putFloat(key, (Float) value);
            } else if(value instanceof Double) {
                values.putString(key,  value.toString());
            } else if(value instanceof Boolean) {
                values.putBoolean(key, (Boolean) value);
            } else {
                throw new IllegalArgumentException("Value must be one of the primitive types (int, String, float, double, long, boolean");
            }
            values.apply();
        }
        return this;
    }

    @Override
    public AppData get(String key) {
        if(contextWeakReference == null || contextWeakReference.get() != null) {
            Context context = contextWeakReference.get();
            return new AppData(context.getSharedPreferences(getClass().getName(), Context.MODE_PRIVATE).getString(key, null));
        }
        return null;
    }
}
