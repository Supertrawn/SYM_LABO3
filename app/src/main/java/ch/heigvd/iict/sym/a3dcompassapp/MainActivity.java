package ch.heigvd.iict.sym.a3dcompassapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import ch.heigvd.iict.sym.a3dcompassapp.activity.BarcodeActivity;
import ch.heigvd.iict.sym.a3dcompassapp.activity.BeaconActivity;
import ch.heigvd.iict.sym.a3dcompassapp.activity.nfc.NFCActivity;
import ch.heigvd.iict.sym.a3dcompassapp.activity.compass.CompassActivity;

public class MainActivity extends AppCompatActivity {

    Button btnNFC;
    Button btnBarcode;
    Button btnBeacon;
    Button btnCompass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main");

        btnNFC = findViewById(R.id.btnNFC);
        btnNFC.setOnClickListener(v -> {
            Intent intent = new Intent(btnNFC.getContext(), NFCActivity.class);
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
