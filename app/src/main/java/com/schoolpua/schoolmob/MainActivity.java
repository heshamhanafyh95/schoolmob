package com.schoolpua.schoolmob;


import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private Button btnlog;
    private EditText inputEmail, inputPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_main);
        inputEmail=(EditText)findViewById(R.id.email);
        inputPassword=(EditText)findViewById(R.id.password);
        btnlog=(Button)findViewById(R.id.btn_login);

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputEmail.getText().toString().matches("")||inputPassword.getText().toString().matches("")){
                    Toast.makeText(MainActivity.this,"please insert username & password",Toast.LENGTH_LONG).show();
                }else{
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("users/"+inputEmail.getText());
                    database.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                            String pass=map.get("password");
                            if (pass.equals(inputPassword.getText().toString())){
                                startActivity(new Intent(MainActivity.this,home.class));
                                finish();
                            }else{
                                Toast.makeText(MainActivity.this,"invalid username or password try again",Toast.LENGTH_LONG).show();
                                inputPassword.setText("");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }
        });


    }

}
