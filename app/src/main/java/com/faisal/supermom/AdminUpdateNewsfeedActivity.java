package com.faisal.supermom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

public class AdminUpdateNewsfeedActivity extends AppCompatActivity {

    DatabaseReference databaseReferenceNewsfeedInfo;


    //Listview
    ListView listViewNewsfeedInfo;

    List<NewNewsfeedClass> newNewsfeedClassList;

    //next page links//
    private TextView goToUpdateNewsfeedInfoPage, goToAddDoctorInfo, goToAddNewsfeedInfo, goToUpdateDoctorInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_newsfeed);







        //database reference
        databaseReferenceNewsfeedInfo = FirebaseDatabase.getInstance().getReference("babyInfo");

        //code for showing doctor info//


        listViewNewsfeedInfo = (ListView)findViewById(R.id.showNewsfeedInfoListViewID);
        newNewsfeedClassList = new ArrayList<>();

        //code for update doctor info starts//

        listViewNewsfeedInfo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                NewNewsfeedClass newsFeedInfo = newNewsfeedClassList.get(position);
                showDialogUpdateDoctorInfo(newsFeedInfo.getNewBabyInfoID(),
                        newsFeedInfo.getNewBabyInfoHeadline(),newsFeedInfo.getNewBabyInfo(),  newsFeedInfo.getNewNewsfeedImageName(),
                        newsFeedInfo.getNewNewsfeedImageUrl());
                return false;
            }
        });

        //code for update baby info ends//





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
                            Toast.makeText(AdminUpdateNewsfeedActivity.this,
                                    "You have been signed out.",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // Close activity
                            startActivity(new Intent(AdminUpdateNewsfeedActivity.this,LoginActivity.class));

                        }
                    });
        }

        if (item.getItemId() == R.id.addNewsfeedInfoMenuNewID){
            startActivity(new Intent(AdminUpdateNewsfeedActivity.this,AdminAddBabyInfoActivity.class));
        }

        if (item.getItemId() == R.id.updateNewsfeedInfoMenuNewID){
            startActivity(new Intent(AdminUpdateNewsfeedActivity.this,AdminUpdateNewsfeedActivity.class));
        }

        if (item.getItemId() == R.id.addDoctorInfoMenuNewID){
            startActivity(new Intent(AdminUpdateNewsfeedActivity.this,AdminAddDoctorActivityNew.class));
        }

        if (item.getItemId() == R.id.updateDoctorInfoMenuNewID){
            startActivity(new Intent(AdminUpdateNewsfeedActivity.this,AdminUpdateDoctorInfoActivity.class));
        }

        if (item.getItemId() == R.id.addVaccineInfoNotificationInfoMenuNewID){
            startActivity(new Intent(AdminUpdateNewsfeedActivity.this,AdminAddNotificationActivity.class));
        }

        return true;
    }

    //Navigation menu code ends here//

    private void showDialogUpdateDoctorInfo(final String newsID, String newsHeadline, String newsInfo,
                                            String newsImageName, String newsImageURL ) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = getLayoutInflater();
        final View diaglogView = inflater.inflate(R.layout.update_dialog_for_update_baby_info,null);
        dialogBuilder.setView(diaglogView);


        final EditText newsfeedInfoUpdateNewsfeedHeadlineEditText =
                (EditText) diaglogView.findViewById(R.id.updateBabyHeadlineID);
        final EditText newsfeedInfoNewsfeedInfoEditText =
                (EditText) diaglogView.findViewById(R.id.updateBabyInfoID);
        final EditText newsfeedInfoNewsFeedImageNameEditText =
                (EditText) diaglogView.findViewById(R.id.updateNewsfeedImageNameID);
        final EditText newsfeedInfoNewsfeedImageURLText =
                (EditText) diaglogView.findViewById(R.id.updateNewsfeedImageURLID);

        Button processUpdateNewsfeedInfo = (Button) diaglogView.findViewById(R.id.processUpdateNewsfeedInfoBtnID);


        dialogBuilder.setTitle("Updating Newsfeed Info");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        processUpdateNewsfeedInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String headline = newsfeedInfoUpdateNewsfeedHeadlineEditText.getText().toString();
                String info = newsfeedInfoNewsfeedInfoEditText.getText().toString();
                String imageName = newsfeedInfoNewsFeedImageNameEditText.getText().toString();
                String imageURL = newsfeedInfoNewsfeedImageURLText.getText().toString();


                if (TextUtils.isEmpty(headline) ){
                    newsfeedInfoUpdateNewsfeedHeadlineEditText.setError("headline is required!!");
                    return;
                }
                if (TextUtils.isEmpty(info) ){
                    newsfeedInfoNewsfeedInfoEditText.setError("Newsfeed info is required!!");
                    return;
                }
                if (TextUtils.isEmpty(imageName) ){
                    newsfeedInfoNewsFeedImageNameEditText.setError("Image name is required!!");
                    return;
                }
                if (TextUtils.isEmpty(imageURL) ){
                    newsfeedInfoNewsfeedImageURLText.setError("New Image Url is required!!");
                    return;
                }

                updateNewsfeedInfo(newsID,headline,info,imageName,imageURL);

                alertDialog.dismiss();
            }
        });



        listViewNewsfeedInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Press and hold any item to update",Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReferenceNewsfeedInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                newNewsfeedClassList.clear();
                for (DataSnapshot newsFeedInfoSnapshotVariable : dataSnapshot.getChildren()){
                    //jotokhn prjonto list e data thakbe totokhn prjonto loop ghurbe
                    NewNewsfeedClass newsfeedInfoObj6 = newsFeedInfoSnapshotVariable.getValue(NewNewsfeedClass.class);
                    newNewsfeedClassList.add(newsfeedInfoObj6);


                }

                AdminNewsfeedInfoListAdapter adapter = new AdminNewsfeedInfoListAdapter(AdminUpdateNewsfeedActivity.this,
                        newNewsfeedClassList);

                listViewNewsfeedInfo.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void updateNewsfeedInfo(String nsID,String nsHeadline,String nsInfo,
                                  String nsImageName, String nsImageURL){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("babyInfo").child(nsID);

        NewNewsfeedClass newsfeedInfo = new NewNewsfeedClass(nsID,nsHeadline,nsInfo,nsImageName,nsImageURL);

        databaseReference.setValue(newsfeedInfo);
        Toast.makeText(getApplicationContext(),"Newsfeed successfully updated",Toast.LENGTH_LONG).show();



    }


}
