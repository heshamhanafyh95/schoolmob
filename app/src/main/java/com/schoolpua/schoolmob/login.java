package com.schoolpua.schoolmob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;


public class login extends AppCompatActivity {

    private Button btnlog;
    private EditText inputPhone, inputPassword;
    private CheckBox rememberMe;
    DocumentReference parents;
    String phone;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    BCrypt bCrypt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputPhone=(EditText)findViewById(R.id.email);
        inputPassword=(EditText)findViewById(R.id.password);
        btnlog=(Button)findViewById(R.id.btn_login);
        rememberMe=(CheckBox)findViewById(R.id.rememberMe);

        TextView textView =(TextView)findViewById(R.id.forgetlbl);
        textView.setClickable(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,forgetPassword.class));
            }
        });
        bCrypt=new BCrypt();

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputPhone.getText().toString().matches("")||inputPassword.getText().toString().matches("")){
                    Toast.makeText(login.this,"please insert username & password",Toast.LENGTH_LONG).show();
                }else{

                    phone=inputPhone.getText().toString();

                    parents = FirebaseFirestore.getInstance().collection("parents").document(String.valueOf(inputPhone.getText()));
                    parents.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            Map<String, Object> map = task.getResult().getData();
                            if(task.isSuccessful()){

                                //if (BCrypt.checkpw(inputPassword.getText().toString(),String.valueOf(map.get("password"))))
                                if (map.get("password").equals(inputPassword.getText().toString()))
                                {
                                    Map<String, Object> token = new HashMap<>();
                                    token.put("token", FirebaseInstanceId.getInstance().getToken());
                                    parents.set(token, SetOptions.merge());

                                    FirebaseMessaging.getInstance().subscribeToTopic("AllDevices");
                                    if (rememberMe.isChecked()){
                                        pref = getApplicationContext().getSharedPreferences("LoginDetails", 0); // 0 - for private mode
                                        editor = pref.edit();
                                        editor.putString("phone", phone);
                                        editor.putString("password", inputPassword.getText().toString());
                                        editor.commit();
                                    }
                                    Intent i = new Intent(login.this, home.class);
                                    i.putExtra("phone", phone);
                                    startActivity(i);
                                    finish();
                                }else{
                                    Toast.makeText(login.this,"invalid username or password try again",Toast.LENGTH_LONG).show();
                                    inputPassword.setText("");
                                }
                            }else{
                                Toast.makeText(login.this,"invalid username or password try again",Toast.LENGTH_LONG).show();
                                inputPassword.setText("");
                            }
                        }
                    });
                }
            }
        });

    }
}
