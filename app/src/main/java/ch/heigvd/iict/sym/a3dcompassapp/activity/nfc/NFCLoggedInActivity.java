package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;


import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ch.heigvd.iict.sym.a3dcompassapp.R;

public class NFCLoggedInActivity extends AppCompatActivity {

    Button securityMaxButton, securityMedButton, securityMinButton;
    TextView level, securityLevelLabel;
    CountDownTimer timer;

    private NfcAdapter nfcAdapter;
    private NFCReader nfcReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_nfc);
        setTitle("NFC");

        int timerForSecurity;

        securityMaxButton = (Button) findViewById(R.id.maxSecurityButton);
        securityMedButton = (Button) findViewById(R.id.mediumSecurityButton);
        securityMinButton = (Button) findViewById(R.id.reset);
        level = (TextView) findViewById(R.id.securityLevel);

        securityLevelLabel = (TextView) findViewById(R.id.time);
        securityLevelLabel.setText("Level de sécurité");

        //TODO use NFC to read information
        nfcReader = new NFCReader();
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        checkNfcAdapter();

        if(savedInstanceState != null){
            timerForSecurity = savedInstanceState.getInt("AUTHENTICATE_MAX",0);
            level.setText(timerForSecurity);
        }

        Long l = 30000l;

        timer = new CountDownTimer(l, 1000) {
            @Override
            public void onTick(long millis ) {
                //Tester le temps
                //Attribuer le niveau de sécurité
                if(millis > 20000){
                    securityLevelLabel.setText("Niveau de sécurité: MAX");
                }
                else if(millis > 10000){
                    securityLevelLabel.setText("Niveau de sécurité: MOYEN");
                }else {
                    securityLevelLabel.setText("Niveau de sécurité: MIN");
                }
                level.setText("seconds remaining:" + (millis  / 1000));
            }

            @Override
            public void onFinish() {
                level.setText("Done");
            }
        }.start();
    }


    private void cancelTimer(){
        if(timer != null){
            timer.cancel();
        }
    }

    private boolean checkNfcAdapter(){
        if(nfcAdapter == null){
            Toast.makeText(this, "NFC is not supported on this device.", Toast.LENGTH_LONG);
            finish();
            return false;
        }

        if(nfcAdapter.isEnabled()){
            return true;
        }else{
            Toast.makeText(this, "Please check if NFC is activated", Toast.LENGTH_SHORT);
            finish();
            return false;
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        /**
         * This method gets called, when a new Intent gets associated with the current activity instance.
         * Instead of creating a new activity, onNewIntent will be called. For more information have a look
         * at the documentation.
         *
         * In our case this method gets called, when the user attaches a Tag to the device.
         */
        if(nfcReader.handleIntent(intent)){
            Toast.makeText(this,"Augmentation du timer et du niveau de sécurité", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        nfcReader.setupForegroundDispatch(this, nfcAdapter);
    }


    @Override
    protected  void onPause() {
        nfcReader.stopForeGroundDispatch(this, nfcAdapter);
        super.onPause();
    }


}
