package com.schoolpua.schoolmob;

import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    DocumentReference bus;
    LatLng location;
    int j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        bus= FirebaseFirestore.getInstance().collection("bus").document("bus 1");
        bus.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String,Object> map = documentSnapshot.getData();
                mMap.clear();
                location = new LatLng(((double)map.get("lat")),((double)map.get("long")));
                mMap.addMarker(new MarkerOptions().position(location).title(String.valueOf(j)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14.0f));
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
                                mMap.clear();
                                location = new LatLng(((double)map.get("lat")),((double)map.get("long")));
                                mMap.addMarker(new MarkerOptions().position(location).title(String.valueOf(j)));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 18.0f));
                            }
                        });
                    }
                });

            }
        }, 0, 1000);

    }

}
