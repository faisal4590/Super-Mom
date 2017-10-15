package com.faisal.supermom;

import android.content.Intent;
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

public class AdminAddHealthMonitorInfoActivity extends AppCompatActivity {

    //database reference
    DatabaseReference databaseHealthInfoReference;

    EditText heightEditText,weightEditText,ageEditText;
    Button processHealthMonitoringBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_health_monitor_info);


        databaseHealthInfoReference = FirebaseDatabase.getInstance().getReference("healthMontorInfo");
        //baby info name er new 1ta tree ke referrence hisabe nibe.. nahole root ke reference hisabe nito

        heightEditText = (EditText)findViewById(R.id.adminAddChildHeightID);
        weightEditText = (EditText)findViewById(R.id.adminAddChildWeightID);
        ageEditText = (EditText)findViewById(R.id.adminAddChildAgeID);

        processHealthMonitoringBtn = (Button)findViewById(R.id.processBtnSaveHealthMonitorInfoBTNID);

        processHealthMonitoringBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHealthReport();
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
                            Toast.makeText(AdminAddHealthMonitorInfoActivity.this,
                                    "You have been signed out.",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // Close activity
                            startActivity(new Intent(AdminAddHealthMonitorInfoActivity.this,LoginActivity.class));

                        }
                    });
        }

        if (item.getItemId() == R.id.addNewsfeedInfoMenuNewID){
            startActivity(new Intent(AdminAddHealthMonitorInfoActivity.this,AdminAddBabyInfoActivity.class));
        }

        if (item.getItemId() == R.id.updateNewsfeedInfoMenuNewID){
            startActivity(new Intent(AdminAddHealthMonitorInfoActivity.this,AdminUpdateNewsfeedActivity.class));
        }

        if (item.getItemId() == R.id.addDoctorInfoMenuNewID){
            startActivity(new Intent(AdminAddHealthMonitorInfoActivity.this,AdminAddDoctorActivityNew.class));
        }

        if (item.getItemId() == R.id.updateDoctorInfoMenuNewID){
            startActivity(new Intent(AdminAddHealthMonitorInfoActivity.this,AdminUpdateDoctorInfoActivity.class));
        }
        if (item.getItemId() == R.id.addVaccineInfoNotificationInfoMenuNewID){
            startActivity(new Intent(AdminAddHealthMonitorInfoActivity.this,AdminAddNotificationActivity.class));
        }

        if (item.getItemId() == R.id.addHealthInfoInfoMenuNewID){
            startActivity(new Intent(AdminAddHealthMonitorInfoActivity.this,AdminAddHealthMonitorInfoActivity.class));
        }

        return true;
    }

    //Navigation menu code ends here//




    private void addHealthReport(){
        String heightVar,weightVar,ageVar;
        heightVar = heightEditText.getText().toString();
        weightVar = weightEditText.getText().toString();
        ageVar = ageEditText.getText().toString();

        if(TextUtils.isEmpty(heightVar) || TextUtils.isEmpty(weightVar)
                || TextUtils.isEmpty(ageVar)){
            Toast.makeText(getApplicationContext(),"Please fill up the field",Toast.LENGTH_SHORT).show();
        }
        else{

            String IDVar = databaseHealthInfoReference.push().getKey();
            AdminHealthMonitorInfoClass healthInfoObj = new AdminHealthMonitorInfoClass(heightVar
                    ,weightVar,ageVar);
            databaseHealthInfoReference.setValue(healthInfoObj);

            databaseHealthInfoReference.setValue(healthInfoObj);

            Toast.makeText(getApplicationContext(),"Health info added",Toast.LENGTH_LONG).show();



        }
    }

}
