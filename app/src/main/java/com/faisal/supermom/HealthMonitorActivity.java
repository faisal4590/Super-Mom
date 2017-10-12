package com.faisal.supermom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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



        childAge = (EditText) findViewById(R.id.enterChildAgeForHealthMonitorID);
        childHeight = (EditText) findViewById(R.id.enterChildHeightForHealthMonitorID);
        childWeight = (EditText) findViewById(R.id.enterChildWeightFOrHealthMonitorID);
        childGender = (Spinner)findViewById(R.id.enterChildGenderForHealthMonitorID);
        processHealthMonitorBtn = (Button) findViewById(R.id.showHealthReportForHealthMonitorID);
        processHealthMonitorBtn.setOnClickListener(this);





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
                            Toast.makeText(HealthMonitorActivity.this,
                                    "You have been signed out.",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // Close activity
                            startActivity(new Intent(HealthMonitorActivity.this,LoginActivity.class));

                        }
                    });
        }

        if (item.getItemId() == R.id.newsfeedMenuNewID){
            startActivity(new Intent(HealthMonitorActivity.this,NewNewsfeedActivity.class));
        }

        if (item.getItemId() == R.id.healthMonitorMenuNewID){
            startActivity(new Intent(HealthMonitorActivity.this,HealthMonitorActivity.class));
        }

        if (item.getItemId() == R.id.remainderMenuNewID){
            startActivity(new Intent(HealthMonitorActivity.this,RemainderActivity.class));
        }

        if (item.getItemId() == R.id.ourSpecialistsMenuNewID){
            startActivity(new Intent(HealthMonitorActivity.this,OurSpecialistActivity.class));
        }

        if (item.getItemId() == R.id.forumMenuNewID){
            startActivity(new Intent(HealthMonitorActivity.this,ForumActivity.class));
        }

        if (item.getItemId() == R.id.aboutUsMenuNewID){
            startActivity(new Intent(HealthMonitorActivity.this,AboutUsActivity.class));
        }

        return true;
    }

    //Navigation menu code ends here//



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
