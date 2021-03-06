package ch.heigvd.iict.sym.a3dcompassapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import ch.heigvd.iict.sym.a3dcompassapp.activity.barcode.BarcodeActivity;
import ch.heigvd.iict.sym.a3dcompassapp.activity.beacon.BeaconActivity;
import ch.heigvd.iict.sym.a3dcompassapp.activity.nfc.NfcActivity;
import ch.heigvd.iict.sym.a3dcompassapp.activity.compass.CompassActivity;

/**
 * @Class       : MainActivity
 * @Author(s)   : Michael Brouchoud, Thomas Lechaire & Kevin Pradervand
 * @Date        : 11.12.2018
 *
 * @Goal        : Main Activity
 *
 * @Comment(s)  : -
 *
 * @See         : AppCompatActivity
 */
public class MainActivity extends AppCompatActivity {

    private Button btnNFC;
    private Button btnBarcode;
    private Button btnBeacon;
    private Button btnCompass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.main));

        btnNFC = findViewById(R.id.btnNFC);
        btnNFC.setOnClickListener(v -> {
            Intent intent = new Intent(btnNFC.getContext(), NfcActivity.class);
            btnNFC.getContext().startActivity(intent);
        });

        btnBarcode = findViewById(R.id.btnBarcode);
        btnBarcode.setOnClickListener(v -> {
            Intent intent = new Intent(btnBarcode.getContext(), BarcodeActivity.class);
            btnBarcode.getContext().startActivity(intent);
        });

        btnBeacon = findViewById(R.id.btnBeacon);
        btnBeacon.setOnClickListener(v -> {
            Intent intent = new Intent(btnBeacon.getContext(), BeaconActivity.class);
            btnBeacon.getContext().startActivity(intent);
        });

        btnCompass = findViewById(R.id.btnCompass);
        btnCompass.setOnClickListener(v -> {
            Intent intent = new Intent(btnCompass.getContext(), CompassActivity.class);
            btnCompass.getContext().startActivity(intent);
        });
    }
}
