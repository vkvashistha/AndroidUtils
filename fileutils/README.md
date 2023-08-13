# AndroidUtils - FileUtils
Utility to perform File related operations such as reading, writing, copying and deleting files


## Usage

### Step 1: Import library

```agsl
implement io.github.geofriendtech:fileutils:1.0.0
```

### Step 2: Initialise BarcodeScanner in `Activity.onCreate()` or `Fragment.onCreate()` method

```agsl
FileManager fileManager = new FileManager(FileUtilityActivity.this, destination);
```
where ``destination`` is the Uri of base directory at which your app have access granted (either implicit e.g. app private space or explicity by asking user to choose destination)

### Step 3: Perform File Operations
#### Write file in app private directory

```agsl
fileManager.writeFile(filePath, content.getBytes(StandardCharsets.UTF_8), false);
```

where ``filePath`` is the path within private app directory. The third argument is ``false``, which can be set to ``true``, if you want to append the content.


#### Read file from app's private directory

```agsl
String content = fileManager.readFileAsString(filePath);
```

## How to read/write file on storage other than App's private storage
Such storage are called Shared Access storage, which is restricted from Android 12. Now you've to ask user to grant access to a specific folder to read and write.
For that, first launch a Destination Chooser program as follows:

```agsl
final StorageAccessManager storageAccessManager = new StorageAccessManager(this);
```
This must be initialised in ``Activity.onCreate()`` or ``Fragment.onCreate()``

Then launch destination chooser wherever it is required:
```agsl
storageAccessManager.openDestinationChooser(new StorageAccessManager.DestinationSelectionListener() {
                        @Override
                        public void onDestinationSelected(int status, Uri destination) {
                            fileManager = new SAFFileManager(FileUtilityActivity.this, destination);
                            try {
                                fileManager.writeFile(filePath, content.getBytes(StandardCharsets.UTF_8), false);
                                Toast.makeText(FileUtilityActivity.this, "File Saved Successfully", Toast.LENGTH_SHORT).show();
                            }catch (IOException e) {
                                Log.e("FileUtilty", "Some error occurred while writing content to file ",e);
                                Toast.makeText(FileUtilityActivity.this, "Some error occurred while writing content to file " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
```

## Future Roadmap
Implement Copy, Delete functionality