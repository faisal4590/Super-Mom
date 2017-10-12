package com.faisal.supermom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
                            Toast.makeText(NewNewsfeedActivity.this,
                                    "You have been signed out.",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // Close activity
                            startActivity(new Intent(NewNewsfeedActivity.this,LoginActivity.class));

                        }
                    });
        }

        if (item.getItemId() == R.id.newsfeedMenuNewID){
            startActivity(new Intent(NewNewsfeedActivity.this,NewNewsfeedActivity.class));
        }

        if (item.getItemId() == R.id.healthMonitorMenuNewID){
            startActivity(new Intent(NewNewsfeedActivity.this,HealthMonitorActivity.class));
        }

        if (item.getItemId() == R.id.remainderMenuNewID){
            startActivity(new Intent(NewNewsfeedActivity.this,RemainderActivity.class));
        }

        if (item.getItemId() == R.id.ourSpecialistsMenuNewID){
            startActivity(new Intent(NewNewsfeedActivity.this,OurSpecialistActivity.class));
        }

        if (item.getItemId() == R.id.forumMenuNewID){
            startActivity(new Intent(NewNewsfeedActivity.this,ForumActivity.class));
        }

        if (item.getItemId() == R.id.aboutUsMenuNewID){
            startActivity(new Intent(NewNewsfeedActivity.this,AboutUsActivity.class));
        }

        return true;
    }

    //Navigation menu code ends here//

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
