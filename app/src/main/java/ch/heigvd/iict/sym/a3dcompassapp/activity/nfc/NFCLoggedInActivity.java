package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import ch.heigvd.iict.sym.a3dcompassapp.R;

public class NFCLoggedInActivity extends AppCompatActivity {

    Button securityMaxButton, securityMedButton, securityMinButton;
    TextView securityLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_nfc);
        setTitle("NFC");

        securityMaxButton = (Button) findViewById(R.id.maxSecurityButton);
        securityMedButton = (Button) findViewById(R.id.mediumSecurityButton);
        securityMinButton = (Button) findViewById(R.id.minSecurityButton);
        securityLevel = (TextView) findViewById(R.id.securityLevel);

        SecurityLevel securityLevel = new SecurityLevel();
        securityLevel.execute(Utils.AUTHENTICATE_MAX);

    }



    private class SecurityLevel extends AsyncTask<Integer, Integer, Integer>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            // TODO Create Timer to low the security level every 30 seconds or so.
            return null;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            securityLevel.setText(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }
}
