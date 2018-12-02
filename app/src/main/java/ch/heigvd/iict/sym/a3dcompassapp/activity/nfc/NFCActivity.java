package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;

import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ch.heigvd.iict.sym.a3dcompassapp.R;

public class NFCActivity extends AppCompatActivity {

    EditText loginEditText, passwordEditText;
    Button loginButton;

    private final String validLogin = "heig";
    private final String validPassword = "sym2018";
    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        setTitle("NFC");


        //initialize components
        loginEditText = (EditText) findViewById(R.id.loginEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setEnabled(false);

        //TODO use NFC to read information
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        boolean validNFC = checkNfcAdapter();

        // TODO Add NFC validation before starting next intent.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAuthValid(loginEditText.getText().toString(), passwordEditText.getText().toString())
                        && validNFC) {

                    loginButton.setEnabled(true);
                    Intent intent = new Intent(loginButton.getContext(),NFCLoggedInActivity.class);
                    intent.putExtra("AUTHENTICATE_MAX", Utils.AUTHENTICATE_MAX);
                    loginButton.getContext().startActivity(intent);
                }
            }
        });



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
        return login.equals(validLogin) && password.equals(validPassword) ? true : false;
    }


}

