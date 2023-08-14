# AndroidUtils - App Data Manager
Utility to store data transparently without worrying about implementing underlying storage model. Currently 3 storage model is supported:-

1. Temporary Storage
2. Shared Preference Storage
3. SQLite Storage Model

## Usage

### Step 1: Import library

```agsl
implement io.github.geofriendtech:appdatamanager:1.0.0
```

### Step 2: Get storage 
For Temporary Storage:


```agsl
AppStorage storage = AppDataManager.getStorage(AppDataManager.STORAGE_TYPE_TEMPORARY);
```

For Shared Preference storage:

```agsl
AppStorage storage = AppDataManager.getStorage(AppDataManager.STORAGE_TYPE_SHARED_PREFERENCE);
```

For SQLite Database storage:

```agsl
AppStorage storage = AppDataManager.getStorage(AppDataManager.STORAGE_TYPE_DATABASE);
```

### Step 3: 
To read a key-value pair:
```agsl
AppStorage.data data = storage.get(key);
String value = data.asString()
```

To write a key-value pair:
```agsl
storage.put(key,value)
```

## Future Roadmap
Support to add and Tabular data and hierarchical data 