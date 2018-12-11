package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;


import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import ch.heigvd.iict.sym.a3dcompassapp.R;

public class NFCLoggedInActivity extends AppCompatActivity {
    private static final long INITIAL_TIME = 30000;
    private static final long COUNT_DOWN_INTERVAL = 1000;

    private TextView time, securityLevelLabel;
    private CountDownTimer timer;

    private NfcAdapter nfcAdapter;
    private NFCReader nfcReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_nfc);
        setTitle(getString(R.string.nfc_security_zone));

        securityLevelLabel = findViewById(R.id.securityLevel);
        time = findViewById(R.id.time);

        securityLevelLabel.setText(getString(R.string.security_level));

        nfcReader = new NFCReader();
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        checkNfcAdapter();

        if(savedInstanceState != null){
            int timerForSecurity = savedInstanceState.getInt("AUTHENTICATE_MAX",0);
            securityLevelLabel.setText(timerForSecurity);
        }

        timer = new CountDownTimer(INITIAL_TIME, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millis ) {
                //Tester le temps
                //Attribuer le niveau de sécurité
                if(millis > 20000){
                    displaySecurityLevel("MAX");
                }
                else if(millis > 10000){
                    displaySecurityLevel("MEDIUM");
                }else {
                    displaySecurityLevel("MIN");
                }
                updateTime(millis  / COUNT_DOWN_INTERVAL);
            }

            @Override
            public void onFinish() {
                time.setText(getString(R.string.done));
            }
        }.start();
    }

    private void displaySecurityLevel(String level) {
        securityLevelLabel.setText(String.format(getString(R.string.security_level)  + " : %s", level));
    }

    private void updateTime(long time) {
        this.time.setText(String.format(Locale.FRANCE, getString(R.string.seconds_remaining_time), time));
    }

    private void checkNfcAdapter(){
        if(nfcAdapter == null){
            Toast.makeText(this, getString(R.string.nfc_not_supported), Toast.LENGTH_LONG).show();
            finish();
        }

        if(!nfcAdapter.isEnabled()){
            Toast.makeText(this, getString(R.string.check_nfc_activated), Toast.LENGTH_SHORT).show();
            finish();
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
            Toast.makeText(this, getString(R.string.security_level_updated), Toast.LENGTH_SHORT).show();
            securityLevelLabel.setText("Niveau de sécurité: MAX");
            timer.cancel();
            timer.start();
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
