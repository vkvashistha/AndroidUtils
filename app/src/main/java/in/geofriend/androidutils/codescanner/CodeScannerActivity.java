package in.geofriend.androidutils.codescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import in.geofriend.androidutils.R;
import in.geofriend.barcodescanner.BarcodeScanner;

public class CodeScannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_scanner);
        findViewById(R.id.btn_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarcodeScanner scanner = new BarcodeScanner(CodeScannerActivity.this);
                scanner.scan(new BarcodeScanner.BarcodeScanResultCallback() {
                    @Override
                    public void onBarcodeScanCompleted(boolean success, String result) {
                        if(success) {
                            ((TextView) findViewById(R.id.tv_scanned_code)).setText(result);
                        } else {
                            ((TextView) findViewById(R.id.tv_scanned_code)).setText("Error");
                        }
                    }
                });
            }
        });
    }
}