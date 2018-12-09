package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import ch.heigvd.iict.sym.a3dcompassapp.R;

public class NFCLoggedInActivity extends AppCompatActivity {

    Button securityMaxButton, securityMedButton, securityMinButton;
    TextView level, securityLevelLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_nfc);
        setTitle("NFC");

        int timerForSecurity;

        securityMaxButton = (Button) findViewById(R.id.maxSecurityButton);
        securityMedButton = (Button) findViewById(R.id.mediumSecurityButton);
        securityMinButton = (Button) findViewById(R.id.minSecurityButton);
        level = (TextView) findViewById(R.id.securityLevel);

        securityLevelLabel = (TextView) findViewById(R.id.securityLevelLabel);
        securityLevelLabel.setText("Level de sécurité");

        if(savedInstanceState != null){
            timerForSecurity = savedInstanceState.getInt("AUTHENTICATE_MAX",0);
            level.setText(timerForSecurity);
        }

        new SecurityLevel().execute(10);

    }



    private class SecurityLevel extends AsyncTask<Integer, Integer, Boolean>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Timer is set, nothing to do
        }

        @Override
        protected void doInBackground(Integer... integers) {
            // TODO Create Timer to low the security level every 30 seconds or so.

            for (int i = 0; i < 10; i++){
                publishProgress(i*100);
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            System.out.println(values);


            //level.setText(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
        }
    }
}
