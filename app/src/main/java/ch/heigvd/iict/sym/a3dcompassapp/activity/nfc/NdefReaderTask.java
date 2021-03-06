package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;

import java.util.Arrays;

/**
 * @Class       : NdefReaderTask
 * @Author(s)   : Michael Brouchoud, Thomas Lechaire & Kevin Pradervand
 * @Date        : 11.12.2018
 *
 * @Goal        : Ndef Reader Task
 *
 * @Comment(s)  : -
 *
 * @See         : AsyncTask
 */
public class NdefReaderTask extends AsyncTask<Tag, Void, Boolean> {
    private CommunicationEventListener listener;

    NdefReaderTask(CommunicationEventListener listener) {
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Tag... params) {
        if(params.length > 0) {
            Tag tag = params[0];

            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                return false;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();

            if(records.length > 0) {
                NdefRecord ndefRecord = records[0];
                return ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN &&
                        Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT);
            }
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result != null) {
            listener.handleResponse(result);
        } else {
            listener.handleResponse(false);
        }
    }
}