package ubishops.hackaton.ca.cejirt;

import android.Manifest;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.location.LocationListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import backend.data.RealTimeEventsDataObject;
import backend.data.ZapDataObject;
import backend.dataReader.EventsOutput;
import backend.dataReader.GetResponse;
import backend.dataReader.WebServiceReader;
import backend.dataReader.ZapReader;

public class ResultsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText editLocation = null;
    private LocationListener locationlistener = null;
    private ImageButton switchToHome;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //Lock orientation
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /******************** Switch to home button*********************/
        switchToHome = (ImageButton) this.findViewById(R.id.switchToHome);
        this.switchToHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                int zoom = getIntent().getIntExtra("zoom", -1);
                boolean eventCheckBox = getIntent().getBooleanExtra("eventCheckBox", true);
                boolean zapCheckBox = getIntent().getBooleanExtra("zapCheckBox", true);
                boolean restaurantsCheckBox  = getIntent().getBooleanExtra("restaurantsCheckBox", true);
                homeIntent.putExtra("eventCheckBox", eventCheckBox);
                homeIntent.putExtra("zapCheckBox", zapCheckBox);
                homeIntent.putExtra("restaurantsCheckBox", restaurantsCheckBox);
                homeIntent.putExtra("zoom", zoom);

                startActivity(homeIntent);
                finish();
            }
        });
        /****************end Switch to home button end *******************/
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationlistener = new MyLocationListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //Request current location (or nearest) check gps or wifi here
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationlistener);
        //Get the best provider
        String provider = locationManager.getBestProvider(new Criteria(), true);
        //Get the last location from the provider
        Location lastKnownLocation = locationManager.getLastKnownLocation(provider);

        //Create a new location with given position
        LatLng deviceLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

        //Add marker to the map
        mMap.addMarker(new MarkerOptions().position(deviceLocation).title("You Are Here"));

        int zoom = getIntent().getIntExtra("zoom", 15);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(deviceLocation, zoom)); //Zoom
        LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;

        //Context issue
        ImageView bar = (ImageView) this.findViewById(R.id.progressBar);

        //settings

        boolean zapCheckBox = getIntent().getBooleanExtra("zapCheckBox", true);

        TextView textView = (TextView) this.findViewById(R.id.info);
        EventsOutput evo = new EventsOutput(mMap, getApplicationContext(), deviceLocation, bar, textView, this);

        /*****************Zap**********************/
        if (zapCheckBox) {
            BufferedReader reader = null;
            ArrayList<ZapDataObject> zapAr = new ArrayList<ZapDataObject>();
            try {
                reader = new BufferedReader(new InputStreamReader(getAssets().open("nodes.csv"), "UTF-8"));
                String completeFile = "";
                String tmp = reader.readLine();
                while (tmp != null) {
                    completeFile += (tmp + "\n");
                    tmp = reader.readLine();
                }
                ZapReader zr = new ZapReader();
                zapAr = zr.getZapDataObjectList(completeFile);
            } catch (IOException e) {
                e.printStackTrace();

            }

            Geocoder geocoder = new Geocoder(getApplicationContext());
            List<Address> addresses;
            Log.d(TAG, zapAr.size() + " ");
            for (int i = 0; i < zapAr.size(); i++) {
                Log.d(TAG, zapAr.get(i) + " ");
                double latitude = 0;
                double longitude = 0;
                ZapDataObject zapDO = zapAr.get(i);
                if (zapDO.getLatitude() != null || zapDO.getLongitude() != null) {
                    String lat = zapDO.getLatitude().replace("\"", "");
                    String lon = zapDO.getLongitude().replace("\"", "");
                    Log.d(TAG, lat + lon);
                    try {
                        latitude = new Double(lat.toString());
                        longitude = new Double(lon.toString());
                        LatLng ll = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(ll).title(zapDO.getName())).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.zappin));
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }
            }
        }

        /*****************end Zap end**********************/


        mMap.getUiSettings().setZoomGesturesEnabled(false);//Disable zoom
        mMap.getUiSettings().setRotateGesturesEnabled(false);

        //Call webservicereader
        boolean eventCheckBox = getIntent().getBooleanExtra("eventCheckBox", true);
        if (eventCheckBox) {
            WebServiceReader wsr = new WebServiceReader();
            wsr.setEvents(evo);
            wsr.getStringFromURL(null);
        } else {
            bar.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * Get location of device
     */
    public class MyLocationListener implements LocationListener {

        private static final String TAG = "";

        @Override
        public void onLocationChanged(Location location) {

            String longitude = "Longitude: " + location.getLongitude();
            Log.v(TAG, longitude);
            String latitude = "Latitude: " + location.getLatitude();
            Log.v(TAG, latitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
