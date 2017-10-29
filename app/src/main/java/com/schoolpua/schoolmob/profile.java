package com.schoolpua.schoolmob;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView proName,proClass,proBusNum,proBusSuper,proBusSuperNum;
    Button callSupervisorBtn;
    DatabaseReference database,database2;
    Map<String,String> map1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_profile);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_profile);
        navigationView.setNavigationItemSelectedListener(this);

        proName=(TextView)findViewById(R.id.profileName);
        proClass=(TextView)findViewById(R.id.profileClass);
        proBusNum=(TextView)findViewById(R.id.profileBusNum);
        proBusSuper=(TextView)findViewById(R.id.profileBusSupervisor);
        proBusSuperNum=(TextView)findViewById(R.id.profileBusSupervisorNum);
        callSupervisorBtn=(Button)findViewById(R.id.callSupervisorBtn);

        database = FirebaseDatabase.getInstance().getReference().child("students/"+childrenAdapter.studentId);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,String> map= (Map<String, String>) dataSnapshot.getValue();
                proName.setText("Name\n"+map.get("name"));
                proClass.setText("Class\n"+map.get("class"));
                proBusNum.setText("Bus Number\n"+map.get("bus number"));

                database2 = FirebaseDatabase.getInstance().getReference().child("bus/"+map.get("bus number"));
                database2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        map1= (Map<String, String>) dataSnapshot.getValue();
                        proBusSuper.setText("Bus Supervisor\n"+map1.get("supervisor"));
                        proBusSuperNum.setText("Supervisor Phone\n"+map1.get("superPhone"));
                        callSupervisorBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",map1.get("superPhone") , null));
                                startActivity(intent);
                            }
                        });
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(profile.this,"canceled",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_profile);
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
            startActivity(new Intent(profile.this,home.class));
            profile.this.finish();
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_profile);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
