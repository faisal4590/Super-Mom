package com.faisal.supermom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OurSpecialistActivity extends AppCompatActivity {


    //Listview
    ListView listViewDoctorInfoNew;

    List<DoctorClassNew> doctorInfoListNew;

    //database reference
    DatabaseReference databaseDoctorInfoNewReference;

    //next page variables//

    private TextView beAnAweosmeMomPage,goTohealthMonitor,goToOurSpecialistPage,
            goToAboutUsPage,goToRemainderPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_specialist);

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

        databaseDoctorInfoNewReference = FirebaseDatabase.getInstance().getReference("doctorInfoNew");
        //artist name er new 1ta tree ke referrence hisabe nibe.. nahole root ke reference hisabe nito

        listViewDoctorInfoNew = (ListView)findViewById(R.id.showDoctorInfoListID);
        doctorInfoListNew = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseDoctorInfoNewReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                doctorInfoListNew.clear();
                for (DataSnapshot doctorInfoSnapshotVariable : dataSnapshot.getChildren()){
                    //jotokhn prjonto list e data thakbe totokhn prjonto loop ghurbe
                    DoctorClassNew doctorInfoObj6 = doctorInfoSnapshotVariable.getValue(DoctorClassNew.class);
                    doctorInfoListNew.add(doctorInfoObj6);


                }

                AdminDoctorInfoListAdapterNew adapter = new AdminDoctorInfoListAdapterNew(OurSpecialistActivity.this,doctorInfoListNew);

                listViewDoctorInfoNew.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
