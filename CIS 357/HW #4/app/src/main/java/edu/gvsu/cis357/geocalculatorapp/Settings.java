package edu.gvsu.cis357.geocalculatorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner miles, degrees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        miles = (Spinner) findViewById(R.id.spinnerMiles);
        degrees = (Spinner) findViewById(R.id.spinnerDegrees);

        miles.setOnItemSelectedListener(this);
        degrees.setOnItemSelectedListener(this);

        List<String> milesOptions = new ArrayList<String>();
        List<String> degreeOptions = new ArrayList<String>();

        milesOptions.add("Kilometers");
        milesOptions.add("Miles");

        degreeOptions.add("Mils");
        degreeOptions.add("Degrees");

        ArrayAdapter<String> milesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, milesOptions);
        ArrayAdapter<String> degreesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, degreeOptions);

        milesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        degreesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        miles.setAdapter(milesAdapter);
        degrees.setAdapter(degreesAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
