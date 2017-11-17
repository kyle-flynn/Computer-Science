package edu.gvsu.cis357.geocalculatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import org.joda.time.DateTime;
import org.parceler.Parcels;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationLookupActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.startLocation) TextView startLoc;
    @BindView(R.id.endlocation) TextView endLoc;
    @BindView(R.id.date) TextView dateView;

    private DatePickerDialog dpDialog;

    private static int PLACE_AUTOCOMPLETE_LOC_ONE = 0;
    private static int PLACE_AUTOCOMPLETE_LOC_TWO = 1;

    private Place p1;
    private Place p2;
    private DateTime date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_lookup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        date = DateTime.now();
        dpDialog = DatePickerDialog.newInstance(
                this, date.getYear(), date.getMonthOfYear() - 1, date.getDayOfMonth());
        dateView.setText(formatted(date));
    }

    @OnClick(R.id.startLocation)
    public void startLocPressed() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_LOC_ONE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.endlocation)
    public void endLocPressed() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_LOC_TWO);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.date)
    public void datePressed() {
        dpDialog.show(getFragmentManager(), "datePicker");
    }

    @OnClick(R.id.fab)
    public void FABPressed() {
        Intent result = new Intent();
        LocationLookup loc = new LocationLookup();
        loc.setOrigLat(p1.getLatLng().latitude);
        loc.setOrigLng(p1.getLatLng().longitude);
        loc.setEndLat(p2.getLatLng().latitude);
        loc.setEndLng(p2.getLatLng().longitude);
        loc.set_timestamp(formatted(date));
        Parcelable parcel = Parcels.wrap(loc);
        result.putExtra("LOC", parcel);
        setResult(RESULT_OK, result);
        finish();
    }

    private String formatted(DateTime d) {
        return d.monthOfYear().getAsShortText(Locale.getDefault()) + " " +
                d.getDayOfMonth() + ", " + d.getYear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_LOC_ONE) {
            if (resultCode == RESULT_OK) {
                p1 = PlaceAutocomplete.getPlace(this, data);
                startLoc.setText(p1.getName());
            } else if (resultCode == RESULT_CANCELED) {
                System.out.println("Cancelled by the user");
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status stat = PlaceAutocomplete.getStatus(this, data);
            }
        } else if (requestCode == PLACE_AUTOCOMPLETE_LOC_TWO) {
            if (resultCode == RESULT_OK) {
                p2 = PlaceAutocomplete.getPlace(this, data);
                endLoc.setText(p2.getName());
            } else if (resultCode == RESULT_CANCELED) {
                System.out.println("Cancelled by the user");
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status stat = PlaceAutocomplete.getStatus(this, data);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth,
                          int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        date = new DateTime(year, monthOfYear+1, dayOfMonth, 0, 0);
        dateView.setText(formatted(date));
    }
}
