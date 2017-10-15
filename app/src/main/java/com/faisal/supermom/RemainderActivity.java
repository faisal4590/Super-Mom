package com.faisal.supermom;

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

public class RemainderActivity extends AppCompatActivity {

    //Listview
    ListView listViewVaccineInfoNew;

    List<AdminAddVaccineInfoClass> vaccineInfoListNew;


    //next page variables//

    private TextView beAnAweosmeMomPage,goTohealthMonitor,goToOurSpecialistPage,
            goToAboutUsPage,goToRemainderPage;

    private TextView vaccineLocationTextView,vaccineDetailsTextView,vaccineAvailableForTextView;

    //database reference
    DatabaseReference databaseVaccineNotificationReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder);

        databaseVaccineNotificationReference = FirebaseDatabase.getInstance().getReference("vaccineInfo");

        listViewVaccineInfoNew = (ListView)findViewById(R.id.showVaccineInfoListID);
        vaccineInfoListNew = new ArrayList<>();


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
                            Toast.makeText(RemainderActivity.this,
                                    "You have been signed out.",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // Close activity
                            startActivity(new Intent(RemainderActivity.this,LoginActivity.class));

                        }
                    });
        }

        if (item.getItemId() == R.id.newsfeedMenuNewID){
            startActivity(new Intent(RemainderActivity.this,NewNewsfeedActivity.class));
        }

        if (item.getItemId() == R.id.healthMonitorMenuNewID){
            startActivity(new Intent(RemainderActivity.this,HealthMonitorActivity.class));
        }

        if (item.getItemId() == R.id.remainderMenuNewID){
            startActivity(new Intent(RemainderActivity.this,RemainderActivity.class));
        }

        if (item.getItemId() == R.id.ourSpecialistsMenuNewID){
            startActivity(new Intent(RemainderActivity.this,OurSpecialistActivity.class));
        }

        if (item.getItemId() == R.id.forumMenuNewID){
            startActivity(new Intent(RemainderActivity.this,ForumActivity.class));
        }

        if (item.getItemId() == R.id.aboutUsMenuNewID){
            startActivity(new Intent(RemainderActivity.this,AboutUsActivity.class));
        }

        return true;
    }

    //Navigation menu code ends here//


    @Override
    protected void onStart() {
        super.onStart();

        databaseVaccineNotificationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                vaccineInfoListNew.clear();
                for (DataSnapshot vaccineInfoSnapshotVariable : dataSnapshot.getChildren()){
                    //jotokhn prjonto list e data thakbe totokhn prjonto loop ghurbe
                    AdminAddVaccineInfoClass vaccineInfoObj12 = vaccineInfoSnapshotVariable.
                            getValue(AdminAddVaccineInfoClass.class);
                    vaccineInfoListNew.add(vaccineInfoObj12);


                }

                VaccineInfoListAdapter adapter2 = new VaccineInfoListAdapter(RemainderActivity.this,vaccineInfoListNew);

                listViewVaccineInfoNew.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
