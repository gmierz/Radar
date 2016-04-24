package ubishops.hackaton.ca.cejirt;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.CheckBox;

public class SettingsActivity extends AppCompatActivity {
    private Button switchToHome; //Name should be changed to cancel for clarity
    private Button apply;
    private SeekBar zoomSeekBar;
    private CheckBox zapCheckBox;
    private CheckBox eventCheckBox;
    private CheckBox restaurantsCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        //Assign checkboxes and seekbars
        zoomSeekBar = (SeekBar) this.findViewById(R.id.seekBar); //used to send info about zoom to main activity
        zapCheckBox = (CheckBox) this.findViewById(R.id.checkBox);
        eventCheckBox = (CheckBox) this.findViewById(R.id.checkBox2);
        restaurantsCheckBox = (CheckBox) this.findViewById(R.id.checkBox3);

        //Set seekbar to previous settings
        int defaultZoom = getIntent().getIntExtra("zoom", -1);
        zapCheckBox.setChecked(getIntent().getBooleanExtra("zapCheckBox", true));
        eventCheckBox.setChecked(getIntent().getBooleanExtra("eventCheckBox", true));
        restaurantsCheckBox.setChecked(getIntent().getBooleanExtra("restaurantsCheckBox", true));
        if (defaultZoom != -1) {
            zoomSeekBar.setProgress((int) ((defaultZoom - 13) / 5f * zoomSeekBar.getMax()));
        } else {
            zoomSeekBar.setProgress(zoomSeekBar.getMax() / 2);
        }

        //This is the zoom that will be passed if the user cancels
        final int zoomCancel = 13 + (Math.round(zoomSeekBar.getProgress() / (float) zoomSeekBar.getMax() * 5));
        final boolean zapCheckBoxCancel = zapCheckBox.isChecked();
        final boolean eventCheckBoxCancel = eventCheckBox.isChecked();
        final boolean restaurantsCheckBoxCancel = restaurantsCheckBox.isChecked();


        apply = (Button) this.findViewById(R.id.apply);
        this.apply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent applyIntent = new Intent(getApplicationContext(), MainActivity.class);

                /***********Send zoom to next activity ************/

                float zoomUnformatted = zoomSeekBar.getProgress();
                int zoom = 13 + (Math.round(zoomUnformatted / zoomSeekBar.getMax() * 5));

                applyIntent.putExtra("zoom", zoom); //Send info about zoom to main activity
                applyIntent.putExtra("zapCheckBox", zapCheckBox.isChecked());
                applyIntent.putExtra("eventCheckBox", eventCheckBox.isChecked());
                applyIntent.putExtra("restaurantsCheckBox", restaurantsCheckBox.isChecked());

                /***********end Send zoom to next activity end************/

                startActivity(applyIntent);
                finish();
            }
        });


        /**********Cancel button*************/

        switchToHome = (Button) this.findViewById(R.id.switchToHome);
        this.switchToHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);

                /***********Send zoom to next activity ************/


                homeIntent.putExtra("zoom", zoomCancel); //Send info about zoom to main activity
                homeIntent.putExtra("zapCheckBox", zapCheckBoxCancel);
                homeIntent.putExtra("eventCheckBox", eventCheckBoxCancel);
                homeIntent.putExtra("restaurantsCheckBox", restaurantsCheckBoxCancel);

                /***********end Send zoom to next activity end************/

                startActivity(homeIntent);
                finish();
            }
        });

        /********** End Cancel button End*************/

    }
}
