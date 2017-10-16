package com.faisal.supermom;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private Button btnLogOut,saveNewBabyInfoBtn;
    private TextView textViewWelcome;

    private EditText newBabyNameEditText;
    //database reference
    DatabaseReference databaseNewBabyInfoReference;

    //next page variables//

    private TextView beAnAweosmeMomPage,goTohealthMonitor,goToOurSpecialistPage,
    goToAboutUsPage,goToRemainderPage,goToForumPage;



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

        databaseNewBabyInfoReference = FirebaseDatabase.getInstance().getReference("newBabyInfo");
        //baby info name er new 1ta tree ke referrence hisabe nibe.. nahole root ke reference hisabe nito




        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            //if user is not logged in
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }


        FirebaseUser user = firebaseAuth.getCurrentUser();//getting the user info


        textViewWelcome = (TextView) findViewById(R.id.welcome);
        textViewWelcome.setText("Welcome "+ user.getEmail());

        newBabyNameEditText = (EditText)findViewById(R.id.newBabyNameEditTextID);





        //btnLogOut.setOnClickListener(this);


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

        /*saveNewBabyInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewBabyInfo();
            }
        });*/



    }


    private void addNewBabyInfo(){
        //code for adding baby info into firebase starts//
        String babyName,birthDate,birthMonth,birthYear;
        babyName = newBabyNameEditText.getText().toString();

        if(TextUtils.isEmpty(babyName)){
            Toast.makeText(getApplicationContext(),"Please fill up the field",Toast.LENGTH_SHORT).show();
        }
        else{

            String ID = databaseNewBabyInfoReference.push().getKey();
            String yearString = Integer.toString(year);
            String monthString = Integer.toString(month);
            String dayString = Integer.toString(day);
            NewBabyInfoProfileClass babyInfoObj = new NewBabyInfoProfileClass(ID,babyName,dayString,monthString,yearString);
            databaseNewBabyInfoReference.child(ID).setValue(babyInfoObj);

            Toast.makeText(getApplicationContext(),"Baby Info added",Toast.LENGTH_LONG).show();

            //code for adding baby info into firebase ends//


        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener(){
        public void onDateSet(DatePicker view, int years, int monthOfYear, int dayOfYear){
            year = years;
            month = monthOfYear + 1;//month 0 die start hoy. tai real month number pawar jonno 1 increment korci
            day = dayOfYear;
            Toast.makeText(getApplicationContext(),year + " / " + month + " / " + day ,Toast.LENGTH_SHORT).show();


            addNewBabyInfo();



        }
    };

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


    //Navigation menu code starts here//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.forum_logout_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_sign_out) {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(HomePageActivity.this,
                                    "You have been signed out.",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // Close activity
                            startActivity(new Intent(HomePageActivity.this,LoginActivity.class));

                        }
                    });
        }

        if (item.getItemId() == R.id.newsfeedMenuNewID){
            startActivity(new Intent(HomePageActivity.this,NewNewsfeedActivity.class));
        }

        if (item.getItemId() == R.id.healthMonitorMenuNewID){
            startActivity(new Intent(HomePageActivity.this,HealthMonitorActivity.class));
        }

        if (item.getItemId() == R.id.remainderMenuNewID){
            startActivity(new Intent(HomePageActivity.this,RemainderActivity.class));
        }

        if (item.getItemId() == R.id.ourSpecialistsMenuNewID){
            startActivity(new Intent(HomePageActivity.this,OurSpecialistActivity.class));
        }

        if (item.getItemId() == R.id.forumMenuNewID){
            startActivity(new Intent(HomePageActivity.this,ForumActivity.class));
        }

        if (item.getItemId() == R.id.aboutUsMenuNewID){
            startActivity(new Intent(HomePageActivity.this,AboutUsActivity.class));
        }

        return true;
    }

    //Navigation menu code ends here//

    @Override
    public void onClick(View view) {
        if (view == btnLogOut){
            firebaseAuth.signOut();
            finish();//finishing the current activity

            startActivity(new Intent(this,LoginActivity.class));
        }



    }


}

