package com.schoolpua.schoolmob;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DocumentReference bus;
    LatLng location;
    int j;

    double[] lattest={30.029766,30.030403,30.030588,30.030704,30.030774,30.030826,
            30.030838,30.030797,30.030676,30.030457,30.030201,30.029624,30.029236,
            30.028289,30.027260,30.026192,30.025853,30.025122,30.024666,30.023438,
            30.023058,30.022769,30.022560,30.022261,30.021883,30.021566,30.021274,
            30.021017,30.020614,30.019924,30.018703,30.017412,30.015878,30.014834,
            30.013977,30.012741,30.011268,30.009240,30.007444,30.005058,30.004221,
            30.003778,30.003089,30.002688,30.002041,30.001647,30.001111,30.000216};
    double[] lngtest={32.467881,32.466841,32.466434,32.466067,32.465764,32.465443,
            32.465040,32.464547,32.464018,32.463462,32.463115,32.462269,32.461717,
            32.460433,32.458995,32.457561,32.457074,32.456061,32.455420,32.453720,
            32.453195,32.452810,32.452558,32.452304,32.452009,32.451778,32.451651,
            32.451545,32.451400,32.451203,32.450893,32.450568,32.450180,32.449941,
            32.449765,32.449400,32.449027,32.448537,32.448061,32.447466,32.447268,
            32.447201,32.447189,32.447213,32.447318,32.447427,32.447615,32.448090};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        // Add a marker in Sydney and move the camera
        location = new LatLng(lattest[0], lngtest[0]);
        mMap.addMarker(new MarkerOptions().position(location).title(String.valueOf(j)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14.0f));
        /*bus= FirebaseFirestore.getInstance().collection("bus").document("bus1");
        bus.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String lat,lng;
                Map<String,Object> map = documentSnapshot.getData();
            }
        });*/
        for (j=1;j<lattest.length;){
            location = new LatLng(lattest[j], lngtest[j]);
            mMap.addMarker(new MarkerOptions().position(location).title(String.valueOf(j)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14.0f));
            j++;

        }

    }

}
