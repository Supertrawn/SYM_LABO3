package ch.heigvd.iict.sym.a3dcompassapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ch.heigvd.iict.sym.a3dcompassapp.R;

public class BeaconActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);
        setTitle("iBeacon");
    }
}
