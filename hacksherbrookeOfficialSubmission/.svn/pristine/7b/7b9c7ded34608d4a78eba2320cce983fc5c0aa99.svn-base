package backend.dataReader;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceActivity;
import android.support.v4.app.ActivityCompat;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.location.LocationListener;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.appdatasearch.GetRecentContextCall;
import com.google.android.gms.games.event.Event;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.Future;

import android.content.res.Resources;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class WebServiceReader implements Runnable {

    private int status = 0;
    private String r = null;
    private final String TAG = "MyActivity";
    private GetResponse.WebComputationFuture future = null;
    private Object lock = new Object();
    private EventsOutput evo;
    private Context context;

    public String getR() {
        return r;
    }

    public void setFuture(GetResponse.WebComputationFuture fut) {
        this.future = fut;
    }

    public void setEvents(EventsOutput evo) {
        this.evo = evo;
    }

    public GetResponse.WebComputationFuture getFuture() {
        return future;
    }

    public void run() {
        getStringFromURL(null);
        synchronized (lock) {
            while (r == null) {
                try {
                    lock.wait(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lock.notifyAll();
        }
    }

    public void getStringFromURL(String url) {
        //http://programmerguru.com/android-tutorial/android-restful-webservice-tutorial-how-to-call-restful-webservice-in-android-part-3/
        String temp = "";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://calendrier.ville.sherbrooke.qc.ca/?type=1454364096", new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                r = response.toString();
                status = 1;
                Log.v(TAG, "index=" + r);
                //future.onWebResult(r);
                evo.makeMarkers(r);

            }

            public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
                r = timeline.toString();
                status = 1;
                Log.v(TAG, "index=" + r);
                future.onWebResult(r);
                return;
            }
            /**
             @Override public void onSuccess(String response) {
             // Hide Progress Dialog
             //prgDialog.hide();
             try {
             r = response;
             WebServiceReader.status = 1;
             }catch(Exception e){
             e.printStackTrace();
             }

             }**/
        });
    }

}
