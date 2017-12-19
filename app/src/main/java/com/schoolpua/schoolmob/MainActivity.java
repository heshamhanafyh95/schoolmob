package com.schoolpua.schoolmob;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    DocumentReference parents;
    SharedPreferences pref;

    String phone,pass,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* phone="01223456789";
        childrenAdapter.studentId="1";
        startActivity(new Intent(MainActivity.this,MapsActivity.class));
        finish();*/
        checkVariables();

    }
    public void checkVariables(){
        pref = getApplicationContext().getSharedPreferences("LoginDetails", 0); // 0 - for private mode
        if(pref.contains("phone")){
            phone=pref.getString("phone",null);
            password=pref.getString("password",null);

            verifyPassword(phone,password);

            Map<String, Object> token = new HashMap<>();
            token.put("token", FirebaseInstanceId.getInstance().getToken());
            parents.set(token, SetOptions.merge());

            FirebaseMessaging.getInstance().subscribeToTopic("AllDevices");

            Intent i = new Intent(MainActivity.this, home.class);
            i.putExtra("phone", phone);
            startActivity(i);
            finish();
        }
        else{
            startActivity(new Intent(MainActivity.this,login.class));
            finish();
        }
    }
    public void verifyPassword(String phone, String password){
        pass=password;
        parents = FirebaseFirestore.getInstance().collection("parents").document(phone);
        parents.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> map = task.getResult().getData();
                if(task.isSuccessful()){
                    if (!map.get("password").equals(pass)){
                        startActivity(new Intent(MainActivity.this,login.class));
                        finish();
                    }
                }
            }
        });
    }
}
