package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ch.heigvd.iict.sym.a3dcompassapp.R;

/**
 * @Class       : NfcActivity
 * @Author(s)   : Michael Brouchoud, Thomas Lechaire & Kevin Pradervand
 * @Date        : 11.12.2018
 *
 * @Goal        : Nfc Activity
 *
 * @Comment(s)  : -
 *
 * @See         : NfcReaderActivity
 */
public class NfcActivity extends NfcReaderActivity {
    private EditText loginEditText, passwordEditText;
    private Button loginButton;

    private static final String validLogin = "heig";
    private static final String validPassword = "sym2018";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        setTitle(getString(R.string.nfc));

        //initialize components
        loginEditText = findViewById(R.id.loginEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setEnabled(false);

        loginButton.setOnClickListener(v -> {
            if(isAuthValid(loginEditText.getText().toString(), passwordEditText.getText().toString())) {

                //Read NFC before starting new intent
                Intent intent = new Intent(loginButton.getContext(), NfcLoggedInActivity.class);
                intent.putExtra("first_authorization_level", AuthorizationLevel.AUTHENTICATE_MAX);
                loginButton.getContext().startActivity(intent);
            } else {
                Toast.makeText(this, getString(R.string.wrong_login_or_password), Toast.LENGTH_LONG).show();
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
        loginButton.setEnabled(getNfcReader().handleIntent(intent));
    }

    /**
     * @brief Check if the credentials are valid
     * @param login The login
     * @param password The password
     * @return true -> credentials ok, false credentials ko
     */
    private boolean isAuthValid(String login, String password) {
        return login.equals(validLogin) && password.equals(validPassword);
    }
}

