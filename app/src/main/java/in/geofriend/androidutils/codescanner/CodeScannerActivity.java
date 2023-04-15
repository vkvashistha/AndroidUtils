package in.geofriend.androidutils.codescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import in.geofriend.androidutils.R;
import in.geofriend.barcodescanner.v2.BarcodeScanner;

public class CodeScannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_scanner);
        BarcodeScanner scanner = new BarcodeScanner(CodeScannerActivity.this);
        findViewById(R.id.btn_scan).setOnClickListener(v -> {
            scanner.scan((success, result) -> {
                if(success) {
                    ((TextView) findViewById(R.id.tv_scanned_code)).setText(result);
                } else {
                    ((TextView) findViewById(R.id.tv_scanned_code)).setText("Error");
                }
            });
        });
    }
}