package com.schoolpua.schoolmob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class notification extends AppCompatActivity {

    TextView title,msg;
    String titlestr,body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        title=(TextView)findViewById(R.id.titleNot);
        msg=(TextView)findViewById(R.id.bodymsg);

        Bundle extras = getIntent().getExtras();
        titlestr= extras.getString("title");
        body= extras.getString("messageBody");
        title.setText(titlestr);
        msg.setText(body);

    }
}
