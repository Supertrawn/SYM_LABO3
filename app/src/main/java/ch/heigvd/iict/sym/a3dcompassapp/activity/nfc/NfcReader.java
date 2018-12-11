package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.util.Log;

class NfcReader {
    private static final String MIME_TEXT_PLAIN = "text/plain";
    private boolean response;

    boolean handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask(response -> {
                    this.response =  response;
                })
                .execute(tag);
            } else {
                Log.d(NfcReader.class.getName(), "Wrong mime type: " + type);
            }
        }
        return response;
    }

    void setupForegroundDispatch(NfcReaderActivity nfcReaderActivity, NfcAdapter nfcAdapter) {
        final Intent intent = new Intent(nfcReaderActivity.getApplicationContext(), nfcReaderActivity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(nfcReaderActivity.getApplicationContext(),0, intent, 0);
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

        nfcAdapter.enableForegroundDispatch(nfcReaderActivity, pendingIntent, filters, techList);

    }

    void stopForeGroundDispatch(NfcReaderActivity nfcReaderActivity, NfcAdapter nfcAdapter) {
        nfcAdapter.disableForegroundDispatch(nfcReaderActivity);
    }

}
