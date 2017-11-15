package edu.gvsu.cis357.geocalculatorapp;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @BindView(R.id.spinnerDegrees) Spinner degrees;
    @BindView(R.id.spinnerMiles) Spinner miles;
    @BindView(R.id.saveFab) FloatingActionButton save;
    Intent returnIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        miles.setOnItemSelectedListener(this);
        degrees.setOnItemSelectedListener(this);

        save.setOnClickListener(click -> saveAndClose());

        List<String> milesOptions = new ArrayList<String>();
        List<String> degreeOptions = new ArrayList<String>();

        milesOptions.add("Kilometers");
        milesOptions.add("Miles");

        degreeOptions.add("Degrees");
        degreeOptions.add("Mils");

        ArrayAdapter<String> milesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, milesOptions);
        ArrayAdapter<String> degreesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, degreeOptions);

        milesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        degreesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        miles.setAdapter(milesAdapter);
        degrees.setAdapter(degreesAdapter);

        returnIntent = new Intent();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getSelectedItem().toString();
        if (item.equalsIgnoreCase("mils") || item.equalsIgnoreCase("degrees")) {
            returnIntent.putExtra("Bearing", item);
        } else {
            returnIntent.putExtra("Distance", item);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void saveAndClose() {
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}
