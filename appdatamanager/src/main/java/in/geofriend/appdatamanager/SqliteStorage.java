package in.geofriend.appdatamanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SqliteStorage extends AppStorage {
    private SQLiteOpenHelper dbHelper;

    @Override
    public SqliteStorage withContext(Context context) {
        dbHelper = new SQLiteOpenHelper(context, context.getPackageName(), null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE IF NOT EXISTS APP_DATA (keyy text, value text, parentKey text)");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
        return this;

    }

    @Override
    public SqliteStorage put(String key, Object value) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("keyy", key);
        if(value instanceof String) {
            values.put("value", value.toString());
        } else if(value instanceof Integer) {
            values.put("value", (Integer) value);
        } else if(value instanceof Float) {
            values.put("value", (Float) value);
        } else if(value instanceof Double) {
            values.put("value", (Double) value);
        } else if(value instanceof Boolean) {
            values.put("value", (Boolean) value);
        } else {
            throw new IllegalArgumentException("Value must be one of the primitive types (int, String, float, double, long, boolean");
        }
        db.insert("APP_DATA", null, values);
        return this;
    }

    @Override
    public AppData get(String key) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT value FROM APP_DATA WHERE keyy=?", new String[] {key});
        if(cursor.moveToFirst()) {
            AppData data =  new AppData(cursor.getString(0));
            cursor.close();
            return data;
        }
        return null;
    }
}
