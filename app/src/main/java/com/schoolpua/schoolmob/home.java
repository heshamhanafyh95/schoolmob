package com.schoolpua.schoolmob;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.Map;

public class home extends AppCompatActivity {

    Button btn_child;
    ArrayList<String> names,classs,studentIds;
    ArrayList<String> profilePic;
    StorageReference mStorageRef;
    ListView childlist;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        childlist=(ListView)findViewById(R.id.childlist);
        names=new ArrayList<String>();
        classs=new ArrayList<String>();
        profilePic=new ArrayList<String>();
        studentIds=new ArrayList<String>();
        final int flag[]=new int[1];
        mStorageRef = FirebaseStorage.getInstance().getReference().child("students/"+MainActivity.phone);
        database = FirebaseDatabase.getInstance().getReference().child("parents/"+MainActivity.phone+"/children");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> level0Nodes = dataSnapshot.getChildren();
                flag[0]=(int)dataSnapshot.getChildrenCount();
                for (int i=1;i<=dataSnapshot.getChildrenCount();i++) {
                    // TODO: handle the post
                    DataSnapshot dataSnapshot1=level0Nodes.iterator().next();
                    Map<String,String> map= (Map<String, String>) dataSnapshot1.getValue();
                    names.add(map.get("name"));
                    classs.add(map.get("class"));
                    studentIds.add(dataSnapshot1.getKey());
                    mStorageRef.child(dataSnapshot1.getKey()+".jpg").getDownloadUrl().addOnCompleteListener(home.this,new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            profilePic.add(task.getResult().toString());
                            Log.v("momo2", profilePic.toString());
                            if (profilePic.size()>=flag[0]){
                                childrenAdapter childrenadapter=new childrenAdapter(home.this,profilePic,names,classs,studentIds);
                                childlist.setAdapter(childrenadapter);
                            }
                        }
                    });
                }
                Log.v("momo",profilePic.toString());
                /*childrenAdapter childrenadapter=new childrenAdapter(home.this,profilePic,names,classs,studentIds);
                childlist.setAdapter(childrenadapter);*/
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(home.this,"canceled",Toast.LENGTH_SHORT).show();
            }
        });
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

}
