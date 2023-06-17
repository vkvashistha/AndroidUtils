package in.geofriend.appdatamanager;

import java.util.HashMap;

public class AppDataManager {
    public static final int STORAGE_TYPE_TEMPORARY = 0;
    public static final int STORAGE_TYPE_SHARED_PREFERENCE = 1;
    public static final int STORAGE_TYPE_DATABASE = 2;
    private HashMap<Integer, Class> storageClasses = new HashMap<>();

    public static AppStorage getStorage(int storageType) {
        switch (storageType) {
            case STORAGE_TYPE_TEMPORARY: return new AppStorage();
            case STORAGE_TYPE_DATABASE: return new SqliteStorage();
            case STORAGE_TYPE_SHARED_PREFERENCE: return new SharedPreferenceStorage();
        }
        throw new IllegalArgumentException("Storage type can only be STORAGE_TYPE_TEMPORARY, STORAGE_TYPE_SHARED_PREFERENCE, STORAGE_TYPE_DATABASE");
    }


}
