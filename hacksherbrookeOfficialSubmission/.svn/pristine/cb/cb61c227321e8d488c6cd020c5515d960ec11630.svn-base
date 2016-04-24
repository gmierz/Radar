package ubishops.hackaton.ca.cejirt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.content.Intent;
import android.app.Activity;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity {

    private ImageButton switchToResults;
    private ImageView switchToSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        /******************** Switch to results button*********************/
        switchToResults = (ImageButton) this.findViewById(R.id.switchToResults);
        this.switchToResults.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent resultsIntent = new Intent(getApplicationContext(), ResultsActivity.class);

                //Pass zoom from settings to activity
                int zoom = getIntent().getIntExtra("zoom", 15);
                boolean eventCheckBox = getIntent().getBooleanExtra("eventCheckBox", true);
                boolean zapCheckBox = getIntent().getBooleanExtra("zapCheckBox", true);
                boolean restaurantsCheckBox = getIntent().getBooleanExtra("restaurantsCheckBox", true);
                resultsIntent.putExtra("eventCheckBox", eventCheckBox);
                resultsIntent.putExtra("zapCheckBox", zapCheckBox);
                resultsIntent.putExtra("restaurantsCheckBox", restaurantsCheckBox);

                if (zoom != -1) {
                    resultsIntent.putExtra("zoom", zoom);
                }

                startActivity(resultsIntent);
                finish();
            }
        });
        /*****************end Switch to results button end*******************/


        /******************** Switch to settings button*********************/
        switchToSettings = (ImageView) this.findViewById(R.id.switchToSettings);
        this.switchToSettings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent SettingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);

                int zoom = getIntent().getIntExtra("zoom", -1);

                boolean eventCheckBox = getIntent().getBooleanExtra("eventCheckBox", true);
                boolean zapCheckBox = getIntent().getBooleanExtra("zapCheckBox", true);
                boolean restaurantsCheckBox = getIntent().getBooleanExtra("restaurantsCheckBox", true);
                SettingsIntent.putExtra("eventCheckBox", eventCheckBox);
                SettingsIntent.putExtra("zapCheckBox", zapCheckBox);
                SettingsIntent.putExtra("restaurantsCheckBox", restaurantsCheckBox);

                SettingsIntent.putExtra("zoom", zoom);

                startActivity(SettingsIntent);
                finish();
            }
        });
        /*****************end Switch to settings button end*******************/
    }

}
