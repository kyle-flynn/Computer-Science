package edu.gvsu.cis357.geocalculatorapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.location.Location;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;

import org.joda.time.DateTime;

import edu.gvsu.cis357.geocalculatorapp.dummy.HistoryContent;


public class MainActivity extends AppCompatActivity {

    EditText latOne, lonOne, latTwo, lonTwo;
    Button calculate, clear;
    TextView distance, bearing;
    String dist, bear;
    double distMult, bearMult;

    Intent settingsIntent, historyIntent;

    static int SETTINGS_RESULT = 0, HISTORY_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.latOne = (EditText) findViewById(R.id.textLatOne);
        this.lonOne = (EditText) findViewById(R.id.textLonOne);
        this.latTwo = (EditText) findViewById(R.id.textLatTwo);
        this.lonTwo = (EditText) findViewById(R.id.textLonTwo);
        this.calculate = (Button) findViewById(R.id.calculateBtn);
        this.clear = (Button) findViewById(R.id.clearBtn);
        this.distance = (TextView) findViewById(R.id.textDist);
        this.bearing = (TextView) findViewById(R.id.textBear);
        this.dist = "km";
        this.bear = "Degrees";
        this.distMult = 1.0;
        this.bearMult = 1.0;

        this.calculate.setOnClickListener((View click) -> {
            try {
    //Found at https://stackoverflow.com/questions/3400028/close-virtual-keyboard-on-button-press
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                    double lat1 = Double.parseDouble(latOne.getText().toString());
                    double lon1 = Double.parseDouble(lonOne.getText().toString());
                    double lat2 = Double.parseDouble(latTwo.getText().toString());
                    double lon2 = Double.parseDouble(lonTwo.getText().toString());
                    Location l1 = new Location("l1");
                    l1.setLatitude(lat1);
                    l1.setLongitude(lon1);
                    Location l2 = new Location("l2");
                    l2.setLatitude(lat2);
                    l2.setLongitude(lon2);
                    float[] results = new float[3];
                    //results[0] dist results[1] initial bearing
                    //android.location.Location.distanceBetween(lat1, lon1, lat2, lon2, results);
                    l1.distanceTo(l2);
                    double d = Math.round((l1.distanceTo(l2) / 10.0) * distMult) / 100.0;
                    distance.setText("Distance: " + String.valueOf(d) + " " + dist);
                    double b = Math.round(l1.bearingTo(l2) * bearMult * 100.0) / 100.0;
                    bearing.setText("Bearing: " + String.valueOf(b) + " " + bear);
                } catch (Exception e) {
                    System.out.println("Calculation failure.");
                }
            // remember the calculation.
            HistoryContent.HistoryItem item = new HistoryContent.HistoryItem(lonOne.getText().toString(),
                    lonOne.getText().toString(), latTwo.getText().toString(), lonTwo.getText().toString(), DateTime.now());
            HistoryContent.addItem(item);
        });

        this.clear.setOnClickListener((View click) -> {
    //Found at https://stackoverflow.com/questions/3400028/close-virtual-keyboard-on-button-press
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                latOne.setText(null);
                latTwo.setText(null);
                lonOne.setText(null);
                lonTwo.setText(null);
                distance.setText("Distance: ");
                bearing.setText("Bearing: ");

        });

        settingsIntent = new Intent(this, Settings.class);
        historyIntent = new Intent(this, HistoryActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this,
                    Settings.class);
            startActivityForResult(settingsIntent, SETTINGS_RESULT );
            return true;
        } else if(item.getItemId() == R.id.action_history) {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivityForResult(historyIntent, HISTORY_RESULT );
            return true;
        }
        return false;
    }


    public void openSettings() {
        startActivityForResult(settingsIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == SETTINGS_RESULT){
                this.dist = data.getStringExtra("Distance");
                this.bear = data.getStringExtra("Bearing");
                System.out.println(this.dist + " | " + this.bear);
                if (this.dist.equalsIgnoreCase("kilometers")) {
                    this.distMult = 1.0;
                } else {
                    this.distMult = 0.621371;
                }
                if (this.bear.equalsIgnoreCase("degrees")) {
                    this.bearMult = 1.0;
                } else {
                    this.bearMult = 17.777778;
                }
                if (this.calculate.callOnClick()) {
                    System.out.println("Click");
                } else {
                    System.out.println("No Click");
                }
            } else if (resultCode == HISTORY_RESULT) {
                    String[] vals = data.getStringArrayExtra("item");
                    this.latOne.setText(vals[0]);
                    this.lonOne.setText(vals[1]);
                    this.latTwo.setText(vals[2]);
                    this.lonTwo.setText(vals[3]);
                    this.calculate.callOnClick();  // code that updates the calcs.

            }
        }
    }

}
