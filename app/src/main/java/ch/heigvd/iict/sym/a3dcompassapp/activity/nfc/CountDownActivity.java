package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Locale;

import ch.heigvd.iict.sym.a3dcompassapp.R;

public class CountDownActivity extends AppCompatActivity {
    private static final long INITIAL_TIME = 10000;
    private static final long COUNT_DOWN_INTERVAL = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_nfc);
        setTitle(getString(R.string.nfc_security_zone));

        TextView time = findViewById(R.id.time);

        time.setText(getString(R.string.ready));

        new CountDownTimer(INITIAL_TIME, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millis ) {
                time.setText(String.format(Locale.FRANCE, getString(R.string.seconds_remaining_time), millis  / COUNT_DOWN_INTERVAL));
            }

            @Override
            public void onFinish() {
                time.setText(getString(R.string.done));
            }
        }.start();
    }
}
