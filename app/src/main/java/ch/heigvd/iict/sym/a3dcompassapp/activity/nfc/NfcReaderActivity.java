package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;

import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ch.heigvd.iict.sym.a3dcompassapp.R;

/**
 * @Class       : NfcReaderActivity
 * @Author(s)   : Michael Brouchoud, Thomas Lechaire & Kevin Pradervand
 * @Date        : 11.12.2018
 *
 * @Goal        : Nfc Reader Activity
 *
 * @Comment(s)  : -
 *
 * @See         : AppCompatActivity
 */
public abstract class NfcReaderActivity extends AppCompatActivity {
    private NfcAdapter nfcAdapter;
    private NfcReader nfcReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        setTitle(getString(R.string.nfc));

        nfcReader = new NfcReader();
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        checkNfcAdapter();
    }

    /**
     * Get the NFC reader
     * @return The NFC reader
     */
    protected NfcReader getNfcReader() {
        return nfcReader;
    }

    /**
     * Check if the nfc is activated and if the device support the NFC technology.
     * Display a message to the user if not.
     */
    private void checkNfcAdapter(){
        if(nfcAdapter == null){
            Toast.makeText(this, getString(R.string.nfc_not_supported), Toast.LENGTH_LONG).show();
            finish();
        }

        if(!nfcAdapter.isEnabled()){
            Toast.makeText(this, getString(R.string.check_nfc_activated), Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        nfcReader.setupForegroundDispatch(this, nfcAdapter);
    }


    @Override
    protected  void onPause() {
        nfcReader.stopForeGroundDispatch(this, nfcAdapter);
        super.onPause();
    }
}
