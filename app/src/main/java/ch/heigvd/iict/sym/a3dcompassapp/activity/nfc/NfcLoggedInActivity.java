package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import ch.heigvd.iict.sym.a3dcompassapp.R;

/**
 * @Class       : NfcLoggedInActivity
 * @Author(s)   : Michael Brouchoud, Thomas Lechaire & Kevin Pradervand
 * @Date        : 11.12.2018
 *
 * @Goal        : Nfc Logged In Activity
 *
 * @Comment(s)  : -
 *
 * @See         : NfcReaderActivity
 */
public class NfcLoggedInActivity extends NfcReaderActivity {
    private static final long COUNT_DOWN_INTERVAL = 1000;

    private TextView time, securityLevelLabel;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_nfc);
        setTitle(getString(R.string.nfc_security_zone));

        securityLevelLabel = findViewById(R.id.securityLevel);
        time = findViewById(R.id.time);

        securityLevelLabel.setText(getString(R.string.security_level));

        AuthorizationLevel authorizationLevel = (AuthorizationLevel)getIntent()
                .getSerializableExtra("first_authorization_level");
        securityLevelLabel.setText(authorizationLevel.getName());

        timer = new CountDownTimer(AuthorizationLevel.INITIAL_TIME, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millis ) {
                if(millis > AuthorizationLevel.AUTHENTICATE_MAX.getMsLevel()){
                    setMaxAuthorizationLevel();
                }
                else if(millis > AuthorizationLevel.AUTHENTICATE_MEDIUM.getMsLevel()){
                    displaySecurityLevel(AuthorizationLevel.AUTHENTICATE_MEDIUM.toString());
                }else {
                    displaySecurityLevel(AuthorizationLevel.AUTHENTICATE_LOW.toString());
                }
                updateTime(millis  / COUNT_DOWN_INTERVAL);
            }

            @Override
            public void onFinish() {
                time.setText(getString(R.string.done));
            }
        }.start();
    }

    /**
     * @bried Display the corresponding level
     * @param level The level to display
     */
    private void displaySecurityLevel(String level) {
        securityLevelLabel.setText(String.format(getString(R.string.security_level)  + " %s", level));
    }

    /**
     * @brief Update the time
     * @param time The time
     */
    private void updateTime(long time) {
        this.time.setText(String.format(Locale.FRANCE, getString(R.string.seconds_remaining_time) + " : %d", time));
    }

    /**
     * @brief Display the max level authorisation
     */
    private void setMaxAuthorizationLevel() {
        displaySecurityLevel(AuthorizationLevel.AUTHENTICATE_MAX.toString());
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
        if(getNfcReader().handleIntent(intent)){
            Toast.makeText(this, getString(R.string.security_level_updated), Toast.LENGTH_SHORT).show();
            setMaxAuthorizationLevel();
            timer.cancel();
            timer.start();
        }
    }
}
