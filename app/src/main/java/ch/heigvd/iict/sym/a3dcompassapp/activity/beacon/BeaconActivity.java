package ch.heigvd.iict.sym.a3dcompassapp.activity.beacon;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.heigvd.iict.sym.a3dcompassapp.R;

/**
 * @Class       : BeaconActivity
 * @Author(s)   : Michael Brouchoud, Thomas Lechaire & Kevin Pradervand
 * @Date        : 11.12.2018
 *
 * @Goal        : Beacon Activity
 *
 * @Comment(s)  : -
 *
 * @See         : AppCompatActivity, BeaconConsumer
 */
public class BeaconActivity extends AppCompatActivity implements BeaconConsumer {
    private BeaconManager beaconManager = null;
    private List<Beacon> beacons = null;
    private BeaconListViewAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);
        setTitle(getString(R.string.ibeacon));

        ListView beaconListView = findViewById(R.id.beacon_list_view);
        TextView empty = findViewById(R.id.beacon_list_empty);

        beacons = new ArrayList<>();

        adapter = new BeaconListViewAdapter(BeaconActivity.this, beacons);
        beaconListView.setEmptyView(empty);
        beaconListView.setAdapter(adapter);
        beaconListView.deferNotifyDataSetChanged();

        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }
    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
                if (collection.size() > 0){
                    beacons.clear();
                    beacons.addAll(collection);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(
                    new Region("myRangingUniqueId", null, null, null)
            );
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Class       : BeaconListViewAdapter
     * @Author(s)   : Michael Brouchoud, Thomas Lechaire & Kevin Pradervand
     * @Date        : 11.12.2018
     *
     * @Goal        : BeaconList View Adaptater
     *
     * @Comment(s)  : -
     *
     * @See         : ArrayAdapter
     */
    private class BeaconListViewAdapter extends ArrayAdapter<Beacon> {
        private class BeaconViewHolder{
            public TextView rssi;
            public TextView minNumber;
            public TextView majNumber;
        }

        BeaconListViewAdapter(Context context, List<Beacon> beacons) {
            super(context, 0, beacons);
        }

        @Override
        public View getView(int position, View convertView, @Nullable ViewGroup parent) {

            if(convertView == null){
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.list_item_beacon,parent, false);
            }

            BeaconViewHolder viewHolder = (BeaconViewHolder) convertView.getTag();
            if(viewHolder == null){
                viewHolder = new BeaconViewHolder();
                viewHolder.rssi = convertView.findViewById(R.id.rssi);
                viewHolder.minNumber = convertView.findViewById(R.id.minNumber);
                viewHolder.majNumber = convertView.findViewById(R.id.majNumber);
                convertView.setTag(viewHolder);
            }

            Beacon beacon = getItem(position);

            if(beacon != null) {
                viewHolder.rssi.setText(
                        String.format(getString(R.string.rssi) + " : %s",
                                beacon.getRssi())
                );
                viewHolder.minNumber.setText(
                        String.format(getString(R.string.min_number) + " : %s",
                                beacon.getBeaconTypeCode())
                );
                viewHolder.majNumber.setText(
                        String.format(getString(R.string.max_number) + " : %s",
                                beacon.getBluetoothName())
                );
            }

            return convertView;
        }
    }
}
