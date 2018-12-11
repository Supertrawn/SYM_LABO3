package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.util.Log;

class NFCReader {

    private static final String MIME_TEXT_PLAIN = "text/plain";
    private static final String TAG = "NfcDemo";
    private boolean b;

    boolean handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask(response -> {
                    b =  response;
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

    void setupForegroundDispatch(Activity activity, NfcAdapter nfcAdapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(),0, intent, 0);
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

        nfcAdapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);

    }

    void stopForeGroundDispatch(Activity activity, NfcAdapter nfcAdapter) {
        nfcAdapter.disableForegroundDispatch(activity);
    }

}
