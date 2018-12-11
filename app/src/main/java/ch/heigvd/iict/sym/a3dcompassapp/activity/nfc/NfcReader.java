package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.util.Log;

/**
 * @Class       : NfcReader
 * @Author(s)   : Michael Brouchoud, Thomas Lechaire & Kevin Pradervand
 * @Date        : 11.12.2018
 *
 * @Goal        : Nfc Reader
 *
 * @Comment(s)  : -
 */
class NfcReader {
    private static final String MIME_TEXT_PLAIN = "text/plain";
    private boolean response;

    /**
     * @biref Read the a NFC info
     * @param intent The intent to read the nfc
     * @return true -> has read the nfc, false -> error when read the nfc
     */
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

    /**
     * @brief Setup the tools to read a NFC
     * @param nfcReaderActivity A NFC reader activity
     * @param nfcAdapter A NFC adapter
     */
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

    /**
     * @brief Release the tools to read NFC
     * @param nfcReaderActivity A NFC reader activity
     * @param nfcAdapter A NFC adapter
     */
    void stopForeGroundDispatch(NfcReaderActivity nfcReaderActivity, NfcAdapter nfcAdapter) {
        nfcAdapter.disableForegroundDispatch(nfcReaderActivity);
    }

}
