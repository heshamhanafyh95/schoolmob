package com.schoolpua.schoolmob;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class callSupervisor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Map<String,Object> map;
    DocumentReference student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_supervisor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_call_supervisor);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_call_supervisor);
        navigationView.setNavigationItemSelectedListener(this);

<<<<<<< HEAD
        student = FirebaseFirestore.getInstance().collection("students").document(childrenAdapter.studentId);
        student.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
=======
        database = FirebaseDatabase.getInstance().getReference().child("students/"+childrenAdapter.studentId);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,String> map= (Map<String, String>) dataSnapshot.getValue();
                database2 = FirebaseDatabase.getInstance().getReference().child("bus/"+map.get("bus number"));
                database2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String,String> map1= (Map<String, String>) dataSnapshot.getValue();
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",map1.get("superPhone") , null));
                        startActivity(intent);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
>>>>>>> 7f4850a56a429f185632da0823a3ff3d2bb3a646
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                map= documentSnapshot.getData();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", String.valueOf(map.get("supervisor phone")), null));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_call_supervisor);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings_profile) {
            startActivity(new Intent(this,home.class));
            finish();
            return true;
        }else if(id == R.id.action_settings_logout){
            startActivity(new Intent(this,MainActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(this,profile.class));
            finish();
        } else if (id == R.id.nav_attendance) {
            startActivity(new Intent(this,attendance.class));
            finish();
        } else if (id == R.id.nav_grade) {
            startActivity(new Intent(this,grade.class));
            finish();
        } else if (id == R.id.nav_timetable) {
            startActivity(new Intent(this,timetable.class));
            finish();
        } else if (id == R.id.nav_activities) {
            startActivity(new Intent(this,activities.class));
            finish();
        } else if (id == R.id.nav_tracking) {
            startActivity(new Intent(this,tracking.class));
            finish();
        } else if (id == R.id.nav_supervisor) {
            startActivity(new Intent(this,callSupervisor.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_call_supervisor);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
