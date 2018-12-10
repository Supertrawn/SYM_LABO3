package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class NFCReader {

    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";
    private boolean b;

    public boolean handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask(response -> {
                    b =  response.equals("test");
                    return true;
                })
                .execute(tag);
            } else {
                Log.d(TAG, "Wrong mime type: " + type);
                b = false;
            }
        }
        return b;
    }

    // TODO Set AppCompatActivity To NFCActivity
    public void setupForegroundDispatch(AppCompatActivity nfcActivity, NfcAdapter nfcAdapter) {
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

    // TODO Set AppCompatActivity To NFCActivity
    public void stopForeGroundDispatch(AppCompatActivity nfcActivity, NfcAdapter nfcAdapter) {
        nfcAdapter.disableForegroundDispatch(nfcActivity);
    }

}
