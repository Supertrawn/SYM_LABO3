package ch.heigvd.iict.sym.a3dcompassapp.activity.barcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import ch.heigvd.iict.sym.a3dcompassapp.R;

public class BarcodeActivity extends AppCompatActivity {
    private IntentIntegrator integrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.bar_code));
        setContentView(R.layout.activity_bar_code);

        integrator = new IntentIntegrator(this);
        integrator.setPrompt(getString(R.string.scan_qr_code));
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        startScanner();
    }

    private void startScanner() {
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                this.finish();
            } else {
                TextView barcodeText = findViewById(R.id.barcodeValue);
                barcodeText.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
