package com.schoolpua.schoolmob;

import android.location.Location;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    DocumentReference bus,student;
    LatLng location;
    int j;
    Marker m;
    MarkerOptions a;
    Button foucsMap;
    TextView dist,dur;
    private String TAG = MainActivity.class.getSimpleName();
    double homeLatt,homeLngg;
    HashMap<String, String> mapInformation;
    String busId,phone,studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        Bundle extras = getIntent().getExtras();
        phone= extras.getString("phone");
        studentId= extras.getString("studentId");

        dist=(TextView)findViewById(R.id.distance);
        dur=(TextView)findViewById(R.id.duration);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private class GetMapinfo extends AsyncTask<Void, Void, Void> {
        double homelat,homelng,buslat,buslng;
        GetMapinfo(double homelat,double homelng,double buslat,double buslng){
            this.buslat=buslat;
            this.buslng=buslng;
            this.homelat=homelat;
            this.homelng=homelng;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+buslat+","+buslng+"&destinations="+homelat+","+homelng;
            String jsonStr = sh.makeServiceCall(url);

//            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONObject mapInfo = jsonObj.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0);

                    String distanceValue = mapInfo.getJSONObject("distance").getString("text");

                    String durationValue = mapInfo.getJSONObject("duration").getString("text");

                    // tmp hash map for single contact
                    mapInformation = new HashMap<>();

                    // adding each child node to HashMap key => value
                    mapInformation.put("distance", distanceValue);
                    mapInformation.put("duration", durationValue);
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    /*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });*/

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });*/
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dist.setText(String.valueOf(mapInformation.get("distance")));
            dur.setText(String.valueOf(mapInformation.get("duration")));
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        student=FirebaseFirestore.getInstance().collection("students").document(studentId);
        student.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                busId=documentSnapshot.getString("busnumber");
                homeLatt=Double.valueOf(documentSnapshot.getString("homelat"));
                homeLngg=Double.valueOf(documentSnapshot.getString("homelng"));
            }
        }).continueWith(new Continuation<DocumentSnapshot, Object>() {
            @Override
            public Object then(@NonNull Task<DocumentSnapshot> task) throws Exception {
                bus= FirebaseFirestore.getInstance().collection("bus").document(task.getResult().getString("busnumber"));
                bus.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map<String,Object> map = documentSnapshot.getData();
                        mMap.clear();
                        location = new LatLng(((double)map.get("lat")),((double)map.get("long")));
                        a = new MarkerOptions().position(location).title(String.valueOf(j));
                        m = mMap.addMarker(a);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 17.0f));
                    }
                });
                return null;
            }
        }).continueWith(new Continuation<Object, Object>() {
            @Override
            public Object then(@NonNull Task<Object> task) throws Exception {
                foucsMap=(Button)findViewById(R.id.foucsMap);
                foucsMap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 18.0f));
                    }
                });
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                bus.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        Map<String,Object> map = documentSnapshot.getData();
                                        location = new LatLng(((double)map.get("lat")),((double)map.get("long")));
                                        m.setPosition(location);
                                        new GetMapinfo(homeLatt,homeLngg,location.latitude,location.longitude).execute();
                                    }
                                });
                            }
                        });
                    }
                }, 0, 3500);
                return null;
            }
        });
    }

}
