package com.schoolpua.schoolmob;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class forgetPassword extends AppCompatActivity {

    private EditText recoveryEmail,recoveryPhone;
    private Button btnrecovery;
    DocumentReference parents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        recoveryEmail=(EditText)findViewById(R.id.recoveryEmail);
        recoveryPhone=(EditText)findViewById(R.id.recoveryPhone);
        btnrecovery=(Button)findViewById(R.id.btn_recovery);

        btnrecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parents = FirebaseFirestore.getInstance().collection("parents").document(recoveryPhone.getText().toString());
                parents.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Map<String, Object> map = task.getResult().getData();
                        if(task.isSuccessful()){
                            if (map.get("email").equals(recoveryEmail.getText().toString())){
                                Thread thread=new Thread(){
                                    @Override
                                    public void run() {
                                        GMailSender sender = new GMailSender("schoolmobpua@gmail.com","pua123456");
                                        try {
                                            String msg="Welcome to our Recovery system<br>to Recovery your password " +
                                                    "<a href=\"http://thegenuis.ddns.net:8080/resetPassword/parents/"+recoveryPhone.getText().toString()+"\">" +
                                                    "Click Here</a> " +
                                                    "<br>OR<br> follow this link<br>" +
                                                    "<a href=\"localhost:8080/projects/school2/public/resetPassword/parents/" + recoveryPhone.getText().toString() + "\">" +
                                                    "localhost:8080/projects/school2/public/resetPassword/parents/" + recoveryPhone.getText().toString()+"</a>";
                                            sender.sendMail("Recovery your password", msg, "schoolmobpua@gmail.com", recoveryEmail.getText().toString());
                                            startActivity(new Intent(forgetPassword.this,login.class));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                thread.start();
                            }else{
                                Toast.makeText(forgetPassword.this,"invalid Email or Phone try again",Toast.LENGTH_LONG).show();
                                recoveryEmail.setText("");
                                recoveryPhone.setText("");
                            }
                        }else{
                            Toast.makeText(forgetPassword.this,"invalid Email or Phone try again",Toast.LENGTH_LONG).show();
                            recoveryEmail.setText("");
                            recoveryPhone.setText("");
                        }
                    }
                });
            }
        });
    }
}
