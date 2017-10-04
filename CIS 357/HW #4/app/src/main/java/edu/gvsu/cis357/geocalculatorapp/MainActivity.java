package edu.gvsu.cis357.geocalculatorapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText latOne, lonOne, latTwo, lonTwo;
    Button calculate, clear;
    TextView distance, bearing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        latOne = (EditText) findViewById(R.id.textLatOne);
        lonOne = (EditText) findViewById(R.id.textLonOne);
        latTwo = (EditText) findViewById(R.id.textLatTwo);
        lonTwo = (EditText) findViewById(R.id.textLonTwo);
        calculate = (Button) findViewById(R.id.calculateBtn);
        clear = (Button) findViewById(R.id.clearBtn);
        distance = (TextView) findViewById(R.id.textDist);
        bearing = (TextView) findViewById(R.id.textBear);

        calculate.setOnClickListener(click -> calculateCoords());
        clear.setOnClickListener(click -> clearFields());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void calculateCoords() {
        System.out.println(latOne.getText());
    }

    public void clearFields() {
        latOne.setText(null);
        latTwo.setText(null);
        lonOne.setText(null);
        lonTwo.setText(null);
        distance.setText(null);
        bearing.setText(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
