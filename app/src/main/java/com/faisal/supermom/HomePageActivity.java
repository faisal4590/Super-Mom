package com.faisal.supermom;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private Button btnLogOut;
    private TextView textViewWelcome;

    //next page variables//

    private TextView beAnAweosmeMomPage,goTohealthMonitor,goToOurSpecialistPage,
    goToAboutUsPage,goToRemainderPage;



    //navigation drawer//
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    //datepicker code//
    private Button birthdatePicker;
    int year,month,day;
    static final int DIALOG_ID=0;

    //database reference
    DatabaseReference databaseBabyInfoReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //database ref..
        databaseBabyInfoReference = FirebaseDatabase.getInstance().getReference("babyInfo");

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
                startActivity(new Intent(getApplicationContext(), NewAboutUsActivity.class));
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


        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            //if user is not logged in
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }


        FirebaseUser user = firebaseAuth.getCurrentUser();//getting the user info


        textViewWelcome = (TextView) findViewById(R.id.welcome);
        textViewWelcome.setText("Welcome "+ user.getEmail());

        btnLogOut = (Button) findViewById(R.id.logOut);



        btnLogOut.setOnClickListener(this);


        //drawer layout code starts//
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open,R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//uporer bam e action bar ta dekha jabe now

        //drawer layout code ends//

        //datepicker code starts here//
        //getting the current date and month which we will show to the datepicker//

        final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day  = cal.get(Calendar.DAY_OF_MONTH);

        //calling the datepicker function//
        showDialogOnButtonClicked();
    }

    public void showDialogOnButtonClicked(){
        birthdatePicker = (Button)findViewById(R.id.birthday);
        birthdatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }

    protected Dialog onCreateDialog(int id){
        if(id == DIALOG_ID){
            return new DatePickerDialog(this, datePickerListener,year,month,day);

        }
        return null;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //navigation bar er kono item e tap korle ki hobe tar code ekhane likhbo..
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            //uporer bam er navigation bar er upor click korle menu expand hobe..
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == btnLogOut){
            firebaseAuth.signOut();
            finish();//finishing the current activity

            startActivity(new Intent(this,LoginActivity.class));
        }



    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener(){
        public void onDateSet(DatePicker view, int years, int monthOfYear, int dayOfYear){
            year = years;
            month = monthOfYear + 1;//month 0 die start hoy. tai real month number pawar jonno 1 increment korci
            day = dayOfYear;
            Toast.makeText(getApplicationContext(),year + " / " + month + " / " + day ,Toast.LENGTH_SHORT).show();
        }
    };
}

