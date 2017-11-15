package edu.gvsu.cis357.geocalculatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    @BindView(R.id.textLatOne) EditText latOne;
    @BindView(R.id.textLonOne) EditText lonOne;
    @BindView(R.id.textLatTwo) EditText latTwo;
    @BindView(R.id.textLonTwo) EditText lonTwo;
    @BindView(R.id.calculateBtn) Button calculate;
    @BindView(R.id.clearBtn) Button clear;
    @BindView(R.id.textDist) TextView distance;
    @BindView(R.id.textBear) TextView bearing;
    @BindView(R.id.locButton) Button locButton;

    private ChildEventListener chEvListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            LocationLookup entry = (LocationLookup) dataSnapshot.getValue(LocationLookup.class);
            entry._key = dataSnapshot.getKey();
            allHistory.add(entry);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            LocationLookup entry = (LocationLookup) dataSnapshot.getValue(LocationLookup.class);
            List<LocationLookup> newHistory = new ArrayList<LocationLookup>();
            for (LocationLookup t : allHistory) {
                if (!t._key.equals(dataSnapshot.getKey())) {
                    newHistory.add(t);
                }
            }
            allHistory = newHistory;
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    String dist, bear;
    DatabaseReference topRef;
    public static List<LocationLookup> allHistory;
    double distMult, bearMult;

    Intent settingsIntent, historyIntent, locationIntent;

    static int SETTINGS_RESULT = 0, HISTORY_RESULT = 1, LOCATION_RESULT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        GoogleApiClient apiClient;
        apiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        MainActivity.allHistory = new ArrayList<LocationLookup>();
        this.dist = "km";
        this.bear = "Degrees";
        this.distMult = 1.0;
        this.bearMult = 1.0;

        this.locButton.setOnClickListener((View click) -> {
            startActivityForResult(locationIntent, LOCATION_RESULT);
        });

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

                    LocationLookup entry = new LocationLookup();
                    entry.setOrigLat(lat1);
                    entry.setOrigLng(lon1);
                    entry.setEndLat(lat2);
                    entry.setEndLng(lon2);
                    DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
                    entry.set_timestamp(fmt.print(DateTime.now()));
                    topRef.push().setValue(entry);
                } catch (Exception e) {
                System.out.println("Calculation failure.");
                }
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
        locationIntent = new Intent(this, LocationLookupActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onResume() {
        super.onResume();
        allHistory.clear();
        topRef = FirebaseDatabase.getInstance().getReference("history");
        topRef.addChildEventListener(chEvListener);
    }

    @Override
    public void onPause(){
        super.onPause();
        topRef.removeEventListener(chEvListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this,
                    Settings.class);
            startActivityForResult(settingsIntent, SETTINGS_RESULT);
            return true;
        } else if(item.getItemId() == R.id.action_history) {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivityForResult(historyIntent, HISTORY_RESULT);
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SETTINGS_RESULT){
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
        } else if (requestCode == HISTORY_RESULT) {
            Parcelable parcel = data.getParcelableExtra("item");
            LocationLookup lookup = Parcels.unwrap(parcel);
            this.latOne.setText(String.valueOf(lookup.getOrigLat()));
            this.lonOne.setText(String.valueOf(lookup.getOrigLng()));
            this.latTwo.setText(String.valueOf(lookup.getEndLat()));
            this.lonTwo.setText(String.valueOf(lookup.getEndLng()));
            this.calculate.callOnClick();  // code that updates the calcs.

        } else if (requestCode == LOCATION_RESULT) {
            Parcelable parcel = data.getParcelableExtra("LOC");
            LocationLookup lookup = Parcels.unwrap(parcel);
            this.latOne.setText(String.valueOf(lookup.getOrigLat()));
            this.lonOne.setText(String.valueOf(lookup.getOrigLng()));
            this.latTwo.setText(String.valueOf(lookup.getEndLat()));
            this.lonTwo.setText(String.valueOf(lookup.getEndLng()));
            this.calculate.callOnClick();
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
