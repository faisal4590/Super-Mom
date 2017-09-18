package com.faisal.supermom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class HealthMonitorActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText childAge;
    private EditText childHeight;
    private EditText childWeight;
    private Button processHealthMonitorBtn;
    private Spinner childGender;

    //next page variables//

    private TextView beAnAweosmeMomPage,goTohealthMonitor,goToOurSpecialistPage,
            goToAboutUsPage,goToRemainderPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_monitor);

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

        childAge = (EditText) findViewById(R.id.enterChildAgeForHealthMonitorID);
        childHeight = (EditText) findViewById(R.id.enterChildHeightForHealthMonitorID);
        childWeight = (EditText) findViewById(R.id.enterChildWeightFOrHealthMonitorID);
        childGender = (Spinner)findViewById(R.id.enterChildGenderForHealthMonitorID);
        processHealthMonitorBtn = (Button) findViewById(R.id.showHealthReportForHealthMonitorID);
        processHealthMonitorBtn.setOnClickListener(this);





    }





    public void onClick(View view) {
        if (view == processHealthMonitorBtn) {


            int age = Integer.parseInt(childAge.getText().toString().trim());
            int height = Integer.parseInt(childHeight.getText().toString());
            int weight = Integer.parseInt(childWeight.getText().toString());
            if (age == 1) {
                if (height <= 51 && weight <= 3.4) {

                    Toast.makeText(getApplicationContext(),"Your baby is underheight and underweight.Follow necessary step to increase your baby growth",
                            Toast.LENGTH_LONG).show();

                }
                if (height >= 58 && weight >= 5.7) {

                    Toast.makeText(getApplicationContext(),"Your baby is overheight and overweight.Follow necessary step to increase your baby growth",
                            Toast.LENGTH_LONG).show();
                }
            }

            if (age == 2) {
                if (height <= 54 && weight <= 4) {

                    Toast.makeText(getApplicationContext(),"Your baby is underheight and underweight.Follow necessary step to increase your baby growth",
                            Toast.LENGTH_LONG).show();

                }
                if (height >= 62 && weight >= 7) {

                    Toast.makeText(getApplicationContext(),"Your baby is overheight and overweight.Follow necessary step to increase your baby growth",
                            Toast.LENGTH_LONG).show();
                }

            }

        }

    }

}
