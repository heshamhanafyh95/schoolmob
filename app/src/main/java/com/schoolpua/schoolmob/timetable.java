package com.schoolpua.schoolmob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class timetable extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DocumentReference student,timetable;
    Map<String,Object> map,map2;
    ImageView timetablePic;
    Button fullScreenBtn;
    String link;
    String phone,studentId;
    Bitmap decodedByte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_timetable);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_timetable);
        navigationView.setNavigationItemSelectedListener(this);

        timetablePic=(ImageView)findViewById(R.id.timetablePic);
        fullScreenBtn=(Button)findViewById(R.id.fullPic);

        Bundle extras = getIntent().getExtras();
        phone= extras.getString("phone");
        studentId= extras.getString("studentId");


        student = FirebaseFirestore.getInstance().collection("students").document(studentId);
        student.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                map= documentSnapshot.getData();

                timetable = FirebaseFirestore.getInstance().collection("timetables").document(String.valueOf(map.get("class")));
                timetable.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        map2= documentSnapshot.getData();

                        byte[] decodedString = Base64.decode(String.valueOf(map2.get("pic")), Base64.DEFAULT);
                        decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        timetablePic.setImageBitmap(decodedByte);

                        fullScreenBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                                decodedByte.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                                String path = MediaStore.Images.Media.insertImage(com.schoolpua.schoolmob.timetable.this.getContentResolver(), decodedByte, "Title", null);
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
                                startActivity(browserIntent);
                            }
                        });
                    }
                });


            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_timetable);
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
            Intent i = new Intent(this, home.class);
            //i.putExtra("studentId", studentId);
            i.putExtra("phone", phone);
            startActivity(i);
            finish();
            return true;
        }else if(id == R.id.action_settings_logout){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("LoginDetails", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(this,login.class));
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
            Intent i = new Intent(this, profile.class);
            i.putExtra("studentId", studentId);
            i.putExtra("phone", phone);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_attendance) {
            Intent i = new Intent(this, attendance.class);
            i.putExtra("studentId", studentId);
            i.putExtra("phone", phone);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_grade) {
            Intent i = new Intent(this, grade.class);
            i.putExtra("studentId", studentId);
            i.putExtra("phone", phone);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_timetable) {
            /*Intent i = new Intent(this, timetable.class);
            i.putExtra("studentId", studentId);
            i.putExtra("phone", phone);
            startActivity(i);
            finish();*/
        } else if (id == R.id.nav_activities) {
            Intent i = new Intent(this, activities.class);
            i.putExtra("studentId", studentId);
            i.putExtra("phone", phone);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_tracking) {
            Intent i = new Intent(this, MapsActivity.class);
            i.putExtra("studentId", studentId);
            i.putExtra("phone", phone);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_supervisor) {
            Intent i = new Intent(this, callSupervisor.class);
            i.putExtra("studentId", studentId);
            i.putExtra("phone", phone);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_timetable);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
