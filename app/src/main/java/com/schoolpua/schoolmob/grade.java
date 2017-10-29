package com.schoolpua.schoolmob;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class grade extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<ArrayList<String>> gradeOfsubject;
    private ArrayList<String> subject;

    DatabaseReference database;

    ListView subjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_grade);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_grade);
        navigationView.setNavigationItemSelectedListener(this);

        gradeOfsubject=new ArrayList<ArrayList<String>>();
        subject=new ArrayList<String>();

        subjectList=(ListView)findViewById(R.id.listsubject);

        database = FirebaseDatabase.getInstance().getReference().child("students/"+childrenAdapter.studentId+"/subjects");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> level0Nodes = dataSnapshot.getChildren();
                for (int i=1;i<=dataSnapshot.getChildrenCount();i++) {
                    // TODO: handle the post
                    DataSnapshot dataSnapshot1=level0Nodes.iterator().next();
                    Map<String,String> map= (Map<String, String>) dataSnapshot1.getValue();
                    subject.add(dataSnapshot1.getKey());
                    ArrayList<String>temp=new ArrayList<String>();
                    temp.add(String.valueOf(map.get("final")));
                    temp.add(String.valueOf(map.get("mid")));
                    temp.add(String.valueOf(map.get("quizzes")));
                    gradeOfsubject.add(temp);
                }
                gradeAdapter gradeadapter=new gradeAdapter(grade.this,gradeOfsubject,subject);
                subjectList.setAdapter(gradeadapter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(grade.this,"canceled",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_grade);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_grade);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
