package backend.dataReader;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import backend.data.RealTimeEventsDataObject;

/**
 * Created by Gregory on 2016-04-24.
 */
public class ThreadedOutput implements Runnable {
    private static final String TAG = "";
    private GoogleMap mMap;
    private Context context;
    private LatLng latlon;
    private String jsonData;

    public ThreadedOutput() {
    }

    public ThreadedOutput(GoogleMap m, Context c, LatLng ll, String jsonData) {
        this.mMap = m;
        this.context = c;
        this.latlon = ll;
        this.jsonData = jsonData;
    }

    @Override
    public void run() {
        RealTimeEventsReader rter = new RealTimeEventsReader();
        ArrayList<RealTimeEventsDataObject> ar = rter.getRealTimeEventsDataObjectList(jsonData);
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;

        for (int i = 0; i < ar.size(); i++) {
            RealTimeEventsDataObject rtedo = ar.get(i);
            Address add = new Address(null);
            String tempLine = rtedo.getAD_NO() + " " + rtedo.getAD_GEN() + " " + rtedo.getAD_LIEN() + " " + rtedo.getAD_MUN();
            add.setAddressLine(0, tempLine);
            try {
                addresses = geocoder.getFromLocationName(tempLine, 1);
                if (addresses.size() > 0) {
                    double latitude = addresses.get(0).getLatitude();
                    double longitude = addresses.get(0).getLongitude();
                    LatLng ll = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(ll).title(rtedo.getDESCRIP()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
