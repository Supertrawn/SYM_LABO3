package ch.heigvd.iict.sym.a3dcompassapp.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.zip.Inflater;

import ch.heigvd.iict.sym.a3dcompassapp.R;

public class CountDownActivity extends AppCompatActivity {

    TextView time;
    Button reset;
    CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        setTitle("Test CountDown");


        time = (TextView) findViewById(R.id.time);
        time.setText("Ready");
        reset = (Button) findViewById(R.id.reset);

        Long l = 10000l;

        timer = new CountDownTimer(l, 1000) {
            @Override
            public void onTick(long millis ) {
                time.setText("seconds remaining:" + (millis  / 1000));
            }

            @Override
            public void onFinish() {
                time.setText("Done");
            }
        }.start();

    }


    private void cancelTimer(){
        if(timer != null){
            timer.cancel();
        }
    }


    private class CountDownAsync extends AsyncTask<Long, Long, Long>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            time.setText("Ready");
        }

        @Override
        protected Long doInBackground(Long... integers) {


            return null;
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Long integer) {
            super.onPostExecute(integer);
        }


    }

}
