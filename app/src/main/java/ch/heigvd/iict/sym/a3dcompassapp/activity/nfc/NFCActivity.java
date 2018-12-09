package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import ch.heigvd.iict.sym.a3dcompassapp.R;

public class NFCActivity extends AppCompatActivity {

    EditText loginEditText, passwordEditText;
    Button loginButton;

    private final String validLogin = "heig";
    private final String validPassword = "sym2018";
    private final String nfcValue = "test";

    private NfcAdapter nfcAdapter;

    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        setTitle("NFC");


        //initialize components
        loginEditText = (EditText) findViewById(R.id.loginEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setEnabled(true);

        //TODO use NFC to read information
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        checkNfcAdapter();

        // TODO Add NFC validation before starting next intent.
        loginButton.setOnClickListener(v -> {
            if(isAuthValid(loginEditText.getText().toString(), passwordEditText.getText().toString())
                    && checkNfcAdapter()) {


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
        handleIntent(intent);
    }


    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask(response -> {
                    if(response.equals("test")){
                        loginButton.setEnabled(true);
                        //Toast TAG NFC OK
                        return true;
                    }else{
                        //Toast wroong tag nf
                        return false;
                    }
                }).execute(tag);
            } else {
                Log.d(TAG, "Wrong mime type: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            // In case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask(response -> {
                        if(response.equals("test")){
                            loginButton.setEnabled(true);
                            //Toast TAG NFC OK
                            return true;
                        }else{
                            //Toast wroong tag nf
                            return false;
                        }
                    }).execute(tag);
                    break;
                }
            }
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

    private boolean isAuthValid(String login, String password) {
        return login.equals(validLogin) && password.equals(validPassword);
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        setupForegroundDispatch(this, nfcAdapter);
    }


    @Override
    protected  void onPause() {
        stopForeGroundDispatch(this, nfcAdapter);

        super.onPause();
    }


    private void setupForegroundDispatch(NFCActivity nfcActivity, NfcAdapter nfcAdapter) {
        final Intent intent = new Intent(nfcActivity.getApplicationContext(), nfcActivity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(nfcActivity.getApplicationContext(),0, intent, 0);
        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);

        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        }catch (IntentFilter.MalformedMimeTypeException e){
            throw new  RuntimeException("Check your mime type.");
        }

        nfcAdapter.enableForegroundDispatch(nfcActivity, pendingIntent, filters, techList);

    }

    private void stopForeGroundDispatch(NFCActivity nfcActivity, NfcAdapter nfcAdapter) {
        nfcAdapter.disableForegroundDispatch(nfcActivity);
    }

}

