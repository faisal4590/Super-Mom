package com.faisal.supermom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class RemainderActivity extends AppCompatActivity {

    //next page variables//

    private TextView beAnAweosmeMomPage,goTohealthMonitor,goToOurSpecialistPage,
            goToAboutUsPage,goToRemainderPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder);


        //page links
        beAnAweosmeMomPage = (TextView) findViewById(R.id.goToBeAnAwesomeMomPageID);

        goTohealthMonitor = (TextView) findViewById(R.id.goToHealthMonitorID);
        goToOurSpecialistPage = (TextView)findViewById(R.id.goToOurSpecialistPageID);
        goToAboutUsPage = (TextView)findViewById(R.id.goToAboutUsPageID);
        goToRemainderPage = (TextView)findViewById(R.id.goToRemainderID);

        beAnAweosmeMomPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), NewNewsfeedActivity.class));
            }
        });



        goTohealthMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), HealthMonitorActivity.class));
            }
        });

        goToOurSpecialistPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), OurSpecialistActivity.class));
            }
        });

        goToAboutUsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
            }
        });

        goToRemainderPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), RemainderActivity.class));
            }
        });

        //page link ends//


    }
}
