package com.schoolpua.schoolmob;


import android.app.Activity;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.view.View;
import android.os.Bundle;
import java.util.List;
import java.util.ArrayList;

public class AttendanceSpinner extends Activity implements AdapterView.OnItemSelectedListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance);
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.attendence_spinner);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> attendence_array = new ArrayList<String>();
        attendence_array.add("Day");

        //the 3rd item show how the selected choice appears in the spinner control
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,attendence_array);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//Drop down layout style - list view with radio button
        spinner.setAdapter(adapter);//apply it to spinner

    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int position, long id) {
        String item = parent.getItemAtPosition(position).toString();// on select items of spinner
        Toast.makeText(parent.getContext(),"Selected: " + item, Toast.LENGTH_LONG).show(); // Show the selected spinner items
    }
    public void onNothingSelected(AdapterView<?> arg0) {}


}
