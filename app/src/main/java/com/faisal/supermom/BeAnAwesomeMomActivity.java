package com.faisal.supermom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
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

public class BeAnAwesomeMomActivity extends AppCompatActivity {

    //Listview
    ListView listViewBabyInfo;

    List<adminBabyInfo> babyInfoList;

    //database reference
    DatabaseReference databaseBabyInfoReference;


    //variables for displaying image in the newsfeed starts//
    private DatabaseReference mDatabaseRef;
    private List<ForNewsfeed_UploadImageNameAndUriClass> imgList;
    private ListView lv;
    private AdapterForNewsfeedImageListView adapter;

    private ProgressDialog progressDialog;

    //variables for displaying image in the newsfeed ends//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_be_an_awesome_mom);

        databaseBabyInfoReference = FirebaseDatabase.getInstance().getReference("babyInfo");
        //artist name er new 1ta tree ke referrence hisabe nibe.. nahole root ke reference hisabe nito

        //listViewBabyInfo = (ListView)findViewById(R.id.showBabyInfoListID);
        babyInfoList = new ArrayList<>();


        //code for displaying newsfeed image starts//


        imgList = new ArrayList<>();

        lv = (ListView)findViewById(R.id.showBabyInfoListID);

        //progress dialoag
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait while loading images..");
        progressDialog.show();

        mDatabaseRef = FirebaseDatabase.getInstance().
                getReference(AdminAddBabyInfoActivity.FIREBASE_DATABASE_PATH);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                //fetch image data from firebase database

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    ForNewsfeed_UploadImageNameAndUriClass  img = snapshot.getValue(ForNewsfeed_UploadImageNameAndUriClass.class);

                    imgList.add(img);


                }

                //list adapter
                adapter = new AdapterForNewsfeedImageListView(BeAnAwesomeMomActivity.this,
                        R.layout.list_layout_for_baby_info,imgList);


                //set adapter for list view
                lv.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });

        //code for displaying newsfeed image ends//


    }


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
                            Toast.makeText(BeAnAwesomeMomActivity.this,
                                    "You have been signed out.",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // Close activity
                            startActivity(new Intent(BeAnAwesomeMomActivity.this,LoginActivity.class));

                        }
                    });
        }

        if (item.getItemId() == R.id.newsfeedMenuNewID){
            startActivity(new Intent(BeAnAwesomeMomActivity.this,NewNewsfeedActivity.class));
        }

        if (item.getItemId() == R.id.healthMonitorMenuNewID){
            startActivity(new Intent(BeAnAwesomeMomActivity.this,HealthMonitorActivity.class));
        }

        if (item.getItemId() == R.id.remainderMenuNewID){
            startActivity(new Intent(BeAnAwesomeMomActivity.this,RemainderActivity.class));
        }

        if (item.getItemId() == R.id.ourSpecialistsMenuNewID){
            startActivity(new Intent(BeAnAwesomeMomActivity.this,OurSpecialistActivity.class));
        }

        if (item.getItemId() == R.id.forumMenuNewID){
            startActivity(new Intent(BeAnAwesomeMomActivity.this,ForumActivity.class));
        }

        if (item.getItemId() == R.id.aboutUsMenuNewID){
            startActivity(new Intent(BeAnAwesomeMomActivity.this,AboutUsActivity.class));
        }

        return true;
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
                    adminBabyInfo babyInfoObj2 = babyInfoSnapshotVariable.getValue(adminBabyInfo.class);
                    babyInfoList.add(babyInfoObj2);


                }

                BabyInfoListAdapter adapter = new BabyInfoListAdapter(BeAnAwesomeMomActivity.this,babyInfoList);

                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
