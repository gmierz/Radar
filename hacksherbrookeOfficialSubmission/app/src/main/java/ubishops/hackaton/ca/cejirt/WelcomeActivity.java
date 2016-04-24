package ubishops.hackaton.ca.cejirt;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        /************************ Delay main activity ***********************/
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, 1500);
        /************************End Delay main activity end***********************/


/**
 try {
 wait(2000);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
 **/

    }
}
