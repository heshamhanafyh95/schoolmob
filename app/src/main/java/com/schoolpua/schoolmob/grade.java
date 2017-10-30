package com.schoolpua.schoolmob;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;


public class grade extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<ArrayList<String>> gradeOfsubject;
    private ArrayList<String> subject;

    Map<String,Object> map;
    DocumentReference student;
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

        student = FirebaseFirestore.getInstance().collection("students").document(childrenAdapter.studentId);
        student.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                map = (Map<String, Object>) task.getResult().getData().get("subjects");
                for (int i=0;i<map.size();i++) {
                    // TODO: handle the post
                    String key=String.valueOf(map.keySet().toArray()[i]);
                    Map<String,String> map1= (Map<String, String>) map.get(key);
                    subject.add(key);
                    ArrayList<String>temp=new ArrayList<String>();
                    temp.add(String.valueOf(map1.get("final")));
                    temp.add(String.valueOf(map1.get("midterm")));
                    temp.add(String.valueOf(map1.get("quizzes")));
                    gradeOfsubject.add(temp);
                }
                gradeAdapter gradeadapter=new gradeAdapter(grade.this,gradeOfsubject,subject);
                subjectList.setAdapter(gradeadapter);
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
