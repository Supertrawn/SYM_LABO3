package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.util.Log;
import android.nfc.Tag;


import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/* *********************
 *
 * NFC READER TASK
 *
 *********************************/
public class NdefReaderTask extends AsyncTask<Tag, Void, Boolean> {

    public static final String TAG = "NfcDemo";
    private CommunicationEventListener listener;

    public NdefReaderTask(CommunicationEventListener listener) {
        this.listener = listener;
    }


    @Override
    protected Boolean doInBackground(Tag... params) {
        Tag tag = params[0];

        Ndef ndef = Ndef.get(tag);
        if (ndef == null) {
            // NDEF is not supported by this Tag.
            return false;
        }

        NdefMessage ndefMessage = ndef.getCachedNdefMessage();

        NdefRecord[] records = ndefMessage.getRecords();
        for (NdefRecord ndefRecord : records) {
            return (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT));
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result != null) {
            listener.handleServerResponse(result);
        }
    }
}