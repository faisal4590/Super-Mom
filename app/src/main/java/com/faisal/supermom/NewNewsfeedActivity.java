package com.faisal.supermom;

import android.app.ProgressDialog;
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

public class NewNewsfeedActivity extends AppCompatActivity {

    //next page variables//

    private TextView beAnAweosmeMomPage,goTohealthMonitor,goToOurSpecialistPage,
            goToAboutUsPage,goToRemainderPage;

    //Listview
    ListView listViewBabyInfo;

    List<NewNewsfeedClass> babyInfoList;

    //database reference
    DatabaseReference databaseBabyInfoReference;

    //variables for displaying image in the newsfeed starts//
    //private DatabaseReference databaseReferenceForImage;
    private NewNewsfeedInfoListAdapter adapter;


    //variables for displaying image in the newsfeed ends//


    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_newsfeed);


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

        //progress bar starts//

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait while loading the newsfeed..");
        progressDialog.show();

        //progress bar ends//

        listViewBabyInfo = (ListView)findViewById(R.id.newShowBabyInfoListID);

        //database reference starts//
        databaseBabyInfoReference = FirebaseDatabase.getInstance().getReference("babyInfo");

        //database reference ends//

        //declaring list view object//
        babyInfoList = new ArrayList<>();




    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseBabyInfoReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                babyInfoList.clear();
                for (DataSnapshot babyInfoSnapshotVariable : dataSnapshot.getChildren()){
                    //jotokhn prjonto list e data thakbe totokhn prjonto loop ghurbe
                    NewNewsfeedClass babyInfoObj2 = babyInfoSnapshotVariable.getValue(NewNewsfeedClass.class);
                    babyInfoList.add(babyInfoObj2);


                }

                progressDialog.dismiss();

                NewNewsfeedInfoListAdapter adapter = new NewNewsfeedInfoListAdapter(NewNewsfeedActivity.this,babyInfoList);

                listViewBabyInfo.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



}
