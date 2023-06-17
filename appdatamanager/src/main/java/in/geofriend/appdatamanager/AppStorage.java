package in.geofriend.appdatamanager;

import android.content.Context;

import java.util.HashMap;

public class AppStorage {
    private static HashMap<String, Object> tempStorage = new HashMap<>();
    public AppStorage withContext(Context context) {
        return this;
    }
    public AppStorage put(String key, Object value) {
        if(tempStorage == null) {
            tempStorage = new HashMap<>();
        }
        tempStorage.put(key, value);
        return this;
    }

    public void putAsync(String key, Object value, OnDataSaved onDataSaved) {
        if(tempStorage == null) {
            tempStorage = new HashMap<>();
        }
        tempStorage.put(key, value);
        if(onDataSaved != null) {
            onDataSaved.onDataSaved();
        }
    }

    public void fetchAsync(String key, OnDataFetched onDataFetched) {
        if(tempStorage == null) {
            tempStorage = new HashMap<>();
        }
        if(onDataFetched!= null) {
            onDataFetched.onDataFetched(new AppData(tempStorage.get(key)));
        }
    }

    public AppData get(String key) {
        if(tempStorage == null) {
            tempStorage = new HashMap<>();
        }
        return new AppData(tempStorage.get(key));
    }

    public void remove(String key) {
        if(tempStorage == null) {
            tempStorage = new HashMap<>();
        }
        tempStorage.remove(key);
    }


    public interface OnDataFetched {
        void onDataFetched(AppData appData);
    }

    public interface OnDataSaved {
        void onDataSaved();
    }

    public static class AppData {
        Object data;

        public AppData(Object value) {
            this.data = value;
        }

        public Object value() {
            return data;
        }

        public int asInt() {
            try {
                if (data instanceof Integer) {
                    return (Integer) data;
                } else if (data instanceof String) {
                    return Integer.parseInt(data.toString());
                } else if (data instanceof Double) {
                    return ((Double) data).intValue();
                } else if (data instanceof Float) {
                    return ((Float) data).intValue();
                } else {
                    throw new RuntimeException("Unable to cast Object as Integer : " + data);
                }
            } catch (Exception e) {
                throw new RuntimeException("Unable to cast Object as Integer : " + data);
            }
        }

        public float asFloat() {
            try {
                if (data instanceof Integer) {
                    return ((Integer) data).floatValue();
                } else if (data instanceof String) {
                    return Float.parseFloat(data.toString());
                } else if (data instanceof Double) {
                    return ((Double) data).floatValue();
                } else if (data instanceof Float) {
                    return ((Float) data);
                } else {
                    throw new RuntimeException("Unable to cast Object as Integer : " + data);
                }
            } catch (Exception e) {
                throw new RuntimeException("Unable to cast Object as Float : " + data);
            }
        }

        public String asString() {
            if(data == null) {
                return null;
            } else {
                return data.toString();
            }
        }

        public double asDouble() {
            try {
                if (data instanceof Integer) {
                    return ((Integer) data).doubleValue();
                } else if (data instanceof String) {
                    return Double.parseDouble(data.toString());
                } else if (data instanceof Double) {
                    return ((Double) data);
                } else if (data instanceof Float) {
                    return ((Float) data).doubleValue();
                } else {
                    throw new RuntimeException("Unable to cast Object as Double : " + data);
                }
            } catch (Exception e) {
                throw new RuntimeException("Unable to cast Object as Double : " + data);
            }
        }
    }
}
