package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import ch.heigvd.iict.sym.a3dcompassapp.R;

public class NFCActivity extends AppCompatActivity {

    private EditText loginEditText, passwordEditText;
    private Button loginButton;

    private static final String validLogin = "heig";
    private static final String validPassword = "sym2018";

    private NfcAdapter nfcAdapter;
    private NFCReader nfcReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        setTitle("NFC");


        //initialize components
        loginEditText = findViewById(R.id.loginEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setEnabled(false);

        nfcReader = new NFCReader();
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        checkNfcAdapter();

        loginButton.setOnClickListener(v -> {
            if(isAuthValid(loginEditText.getText().toString(), passwordEditText.getText().toString())) {

                //Read NFC before starting new intent
                Intent intent = new Intent(loginButton.getContext(),NFCLoggedInActivity.class);
                intent.putExtra("AUTHENTICATE_MAX", Utils.AUTHENTICATE_MAX);
                loginButton.getContext().startActivity(intent);
            }
        });
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
        loginButton.setEnabled(nfcReader.handleIntent(intent));
    }


    private boolean checkNfcAdapter(){
        if(nfcAdapter == null){
            Toast.makeText(this, "NFC is not supported on this device.", Toast.LENGTH_LONG).show();
            finish();
            return false;
        }

        if(nfcAdapter.isEnabled()){
            return true;
        }else{
            Toast.makeText(this, "Please check if NFC is activated", Toast.LENGTH_SHORT).show();
            finish();
            return false;
        }
    }

    private boolean isAuthValid(String login, String password) {
        return login.equals(validLogin) && password.equals(validPassword);
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

