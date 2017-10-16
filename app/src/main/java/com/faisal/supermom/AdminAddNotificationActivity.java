package com.faisal.supermom;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminAddNotificationActivity extends AppCompatActivity {

    EditText vaccineCampaignLocation, vaccineCampaignDetails,vaccineCampaignForWhom;
    Button saveAddAdminVaccineInfoNotification;

    //database reference
    DatabaseReference databaseVaccineNotificationReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_notification);

        databaseVaccineNotificationReference = FirebaseDatabase.getInstance().getReference("vaccineInfo");

        vaccineCampaignLocation = (EditText)findViewById(R.id.adminAddVaccineCampaignLocationID);
        vaccineCampaignDetails = (EditText)findViewById(R.id.adminAddVaccineCampaignDetailsID);
        vaccineCampaignForWhom= (EditText)findViewById(R.id.adminAddVaccineCampaignAvailableForID);

        saveAddAdminVaccineInfoNotification = (Button)findViewById(R.id.processAddVaccineInfoNotificationID);

        databaseVaccineNotificationReference = FirebaseDatabase.getInstance().getReference("vaccineInfo");

        saveAddAdminVaccineInfoNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVaccineInfo();
            }
        });

    }

    //Navigation menu code starts here//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_admin, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_sign_out) {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(AdminAddNotificationActivity.this,
                                    "You have been signed out.",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // Close activity
                            startActivity(new Intent(AdminAddNotificationActivity.this,LoginActivity.class));

                        }
                    });
        }

        if (item.getItemId() == R.id.addNewsfeedInfoMenuNewID){
            startActivity(new Intent(AdminAddNotificationActivity.this,AdminAddBabyInfoActivity.class));
        }

        if (item.getItemId() == R.id.updateNewsfeedInfoMenuNewID){
            startActivity(new Intent(AdminAddNotificationActivity.this,AdminUpdateNewsfeedActivity.class));
        }

        if (item.getItemId() == R.id.addDoctorInfoMenuNewID){
            startActivity(new Intent(AdminAddNotificationActivity.this,AdminAddDoctorActivityNew.class));
        }

        if (item.getItemId() == R.id.updateDoctorInfoMenuNewID){
            startActivity(new Intent(AdminAddNotificationActivity.this,AdminUpdateDoctorInfoActivity.class));
        }

        if (item.getItemId() == R.id.addVaccineInfoNotificationInfoMenuNewID){
            startActivity(new Intent(AdminAddNotificationActivity.this,AdminAddNotificationActivity.class));
        }

        if (item.getItemId() == R.id.addHealthInfoInfoMenuNewID){
            startActivity(new Intent(AdminAddNotificationActivity.this,AdminAddHealthMonitorInfoActivity.class));
        }

        return true;
    }

    //Navigation menu code ends here//


    private void addVaccineInfo(){
        String vaccineLocationVar,vaccineDetailsVar,vaccineAvailableForVar;
        vaccineLocationVar = vaccineCampaignLocation.getText().toString();
        vaccineDetailsVar = vaccineCampaignDetails.getText().toString();
        vaccineAvailableForVar = vaccineCampaignForWhom.getText().toString();

        if(TextUtils.isEmpty(vaccineLocationVar) || TextUtils.isEmpty(vaccineDetailsVar)
                || TextUtils.isEmpty(vaccineAvailableForVar)){
            Toast.makeText(getApplicationContext(),"Please fill up the field",Toast.LENGTH_SHORT).show();
        }
        else{

            String IDVar = databaseVaccineNotificationReference.push().getKey();
            AdminAddVaccineInfoClass vaccineInfoObj = new AdminAddVaccineInfoClass(IDVar,vaccineLocationVar
                    ,vaccineDetailsVar,vaccineAvailableForVar);
            databaseVaccineNotificationReference.child(IDVar).setValue(vaccineInfoObj);

            //databaseVaccineNotificationReference.setValue(vaccineInfoObj);

            Toast.makeText(getApplicationContext(),"Vaccine info added",Toast.LENGTH_LONG).show();

            //code for notification starts here//

            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND,1);//ami chai 5 sec por notification ashbe

            Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");

            notificationIntent.addCategory("android.intent.category.DEFAULT");

            PendingIntent broadcast = PendingIntent.getBroadcast(this,100,notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),broadcast);

            //code for notification ends here//



        }
    }

}
