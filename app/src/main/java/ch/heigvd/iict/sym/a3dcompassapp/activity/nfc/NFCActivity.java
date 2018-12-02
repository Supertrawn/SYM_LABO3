package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;

import android.content.Intent;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        setTitle("NFC");


        //initialize components
        loginEditText = (EditText) findViewById(R.id.loginEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);


        loginButton.setOnClickListener(v -> {
            if(isAuthValid(loginEditText.getText().toString(), passwordEditText.getText().toString())) {
                Intent intent = new Intent(loginButton.getContext(), NFCLoggedInActivity.class);
                intent.putExtra("AUTHENTICATE_MAX", Utils.AUTHENTICATE_MAX);
                loginButton.getContext().startActivity(intent);
            } else {
                Toast.makeText(loginButton.getContext(), "Wrong login or passord", Toast.LENGTH_SHORT).show();
            }
        });

        //TODO use NFC to read information

    }

    private boolean isAuthValid(String login, String password) {
        return login.equals(validLogin) && password.equals(validPassword);
    }


}

