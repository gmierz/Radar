package backend.dataReader;

import android.app.Activity;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import backend.data.RealTimeEventsDataObject;
import ubishops.hackaton.ca.cejirt.R;

import static com.google.android.gms.internal.zzir.runOnUiThread;


/**
 * Created by Gregory on 2016-04-23.
 */
public class EventsOutput {
    private static final String TAG = "";
    private GoogleMap mMap;
    private Context context;
    private LatLng latlon;
    private LatLng templl;
    private LatLngBounds bounds = null;
    ImageView bar;
    Activity activity;
    TextView textView;

    public EventsOutput() {
    }

    public EventsOutput(GoogleMap m, Context c, LatLng ll, ImageView bar, TextView textView, Activity activity) {
        this.activity = activity;
        this.bar=bar;
        this.mMap = m;
        this.context = c;
        this.latlon = ll;
        this.textView = textView;
        bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
    }


    public boolean localCheck(Address temp) {
        templl = new LatLng(temp.getLatitude(), temp.getLongitude());
        if (!bounds.contains(templl)) {
            return false;
        }
        return true;
    }


    public void makeMarkers(final String jsonData) {

        runOnUiThread(new Runnable() {
            private final String jsons = jsonData;

            @Override
            public void run() {
                RealTimeEventsReader rter = new RealTimeEventsReader();
                ArrayList<RealTimeEventsDataObject> ar = rter.getRealTimeEventsDataObjectList(jsons);
                Geocoder geocoder = new Geocoder(context);
                List<Address> addresses;
                ArrayList<Marker> markers = new ArrayList<Marker>();

                for (int i = 0; i < ar.size(); i++) {

                    RealTimeEventsDataObject rtedo = ar.get(i);
                    Address add = new Address(null);
                    if (rtedo.getAD_NO() == null || rtedo.getAD_GEN() == null) {
                        Log.v(TAG, "" + i);
                        continue;
                    }
                    String tempLine = rtedo.getAD_NO() + " " + rtedo.getAD_GEN() + " " + rtedo.getAD_LIEN() + " " + rtedo.getAD_MUN();
                    add.setAddressLine(0, tempLine);
                    try {

                        addresses = geocoder.getFromLocationName(tempLine, 1);


                        addresses = geocoder.getFromLocationName(tempLine, 1);
                        if (addresses.size() > 0 /**&& (localCheck(addresses.get(i)))**/) {
                            double latitude = addresses.get(0).getLatitude();
                            double longitude = addresses.get(0).getLongitude();
                            LatLng ll = new LatLng(latitude, longitude);
                            Marker m = mMap.addMarker(new MarkerOptions().position(ll).title(rtedo.getDESCRIP()));
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.eventpin));
                            markers.add(m); //Keeps track of markes, i think it is useless for now
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                bar.setVisibility(View.INVISIBLE);
                bar.invalidate();



                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        textView.setText(marker.getTitle());
                        return true;
                    }
                });
            }

        });

    }

}
