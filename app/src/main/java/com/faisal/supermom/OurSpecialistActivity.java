package com.faisal.supermom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class OurSpecialistActivity extends AppCompatActivity {


    //Listview
    ListView listViewDoctorInfoNew;

    List<DoctorClassNew> doctorInfoListNew;

    //database reference
    DatabaseReference databaseDoctorInfoNewReference;

    //next page variables//

    private TextView beAnAweosmeMomPage,goTohealthMonitor,goToOurSpecialistPage,
            goToAboutUsPage,goToRemainderPage;

    Button makeTheCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_specialist);



        databaseDoctorInfoNewReference = FirebaseDatabase.getInstance().getReference("doctorInfoNew");
        //artist name er new 1ta tree ke referrence hisabe nibe.. nahole root ke reference hisabe nito

        listViewDoctorInfoNew = (ListView)findViewById(R.id.showDoctorInfoListID);
        doctorInfoListNew = new ArrayList<>();



        makeTheCall = (Button)findViewById(R.id.callDocID);
        makeTheCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "01521201537";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });



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
                            Toast.makeText(OurSpecialistActivity.this,
                                    "You have been signed out.",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // Close activity
                            startActivity(new Intent(OurSpecialistActivity.this,LoginActivity.class));

                        }
                    });
        }

        if (item.getItemId() == R.id.newsfeedMenuNewID){
            startActivity(new Intent(OurSpecialistActivity.this,NewNewsfeedActivity.class));
        }

        if (item.getItemId() == R.id.healthMonitorMenuNewID){
            startActivity(new Intent(OurSpecialistActivity.this,HealthMonitorActivity.class));
        }

        if (item.getItemId() == R.id.remainderMenuNewID){
            startActivity(new Intent(OurSpecialistActivity.this,RemainderActivity.class));
        }

        if (item.getItemId() == R.id.ourSpecialistsMenuNewID){
            startActivity(new Intent(OurSpecialistActivity.this,OurSpecialistActivity.class));
        }

        if (item.getItemId() == R.id.forumMenuNewID){
            startActivity(new Intent(OurSpecialistActivity.this,ForumActivity.class));
        }

        if (item.getItemId() == R.id.aboutUsMenuNewID){
            startActivity(new Intent(OurSpecialistActivity.this,AboutUsActivity.class));
        }

        return true;
    }

    //Navigation menu code ends here//

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
