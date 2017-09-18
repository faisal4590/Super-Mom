package com.faisal.supermom;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminUpdateDoctorInfoActivity extends AppCompatActivity {

    DatabaseReference databaseReferenceDoctorInfo;


    //Listview
    ListView listViewDoctorInfo;

    List<DoctorClassNew> doctorInfoListNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_doctor_info);




        //database reference
        databaseReferenceDoctorInfo = FirebaseDatabase.getInstance().getReference("doctorInfoNew");

        //code for showing doctor info//

        databaseReferenceDoctorInfo = FirebaseDatabase.getInstance().getReference("doctorInfoNew");
        //artist name er new 1ta tree ke referrence hisabe nibe.. nahole root ke reference hisabe nito

        listViewDoctorInfo = (ListView)findViewById(R.id.showDoctorInfoListViewID);
        doctorInfoListNew = new ArrayList<>();

        //code end for showing baby info


        //code for update doctor info starts//

        listViewDoctorInfo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                DoctorClassNew docInfo = doctorInfoListNew.get(position);
                showDialogUpdateDoctorInfo(docInfo.getDoctorIDVar(),
                        docInfo.getDoctorNameVar(),docInfo.getDoctorHospitalVar(),  docInfo.getDoctorAvailibilityVar(),
                        docInfo.getDoctorSpecialityVar(),docInfo.getDoctorContactNoVar());
                return false;
            }
        });

        //code for update baby info ends//



    }

    private void showDialogUpdateDoctorInfo(final String docID, String docName, String docHospital,
                                            String docAvailibility, String docSpeciality, String docContactNo ) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = getLayoutInflater();
        final View diaglogView = inflater.inflate(R.layout.update_dialog_for_update_doctor_info,null);
        dialogBuilder.setView(diaglogView);


        final EditText doctorInfoUpdateDoctorNameEditText = (EditText) diaglogView.findViewById(R.id.updateDoctorNameID);
        final EditText doctorInfoUpdateDoctorHospitalEditText = (EditText) diaglogView.findViewById(R.id.updateDoctoHospitalNameID);
        final EditText doctorInfoUpdateDoctorAvailibilityEditText = (EditText) diaglogView.findViewById(R.id.updateDoctorAvailibilityID);
        final EditText doctorInfoUpdateSpecialityEditText = (EditText) diaglogView.findViewById(R.id.updateDoctorSpecializedInID);
        final EditText doctorInfoUpdateContactNoEditText = (EditText) diaglogView.findViewById(R.id.updateDoctorContactNoID);

        Button processUpdateDoctorInfo = (Button) diaglogView.findViewById(R.id.processUpdateDoctorInfoBtnID);


        dialogBuilder.setTitle("Updating Doctor Info");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        processUpdateDoctorInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = doctorInfoUpdateDoctorNameEditText.getText().toString();
                String hospital = doctorInfoUpdateDoctorHospitalEditText.getText().toString();
                String availibilty = doctorInfoUpdateDoctorAvailibilityEditText.getText().toString();
                String speciality = doctorInfoUpdateSpecialityEditText.getText().toString();
                String contact = doctorInfoUpdateContactNoEditText.getText().toString();


                if (TextUtils.isEmpty(name) ){
                    doctorInfoUpdateDoctorNameEditText.setError("name is required!!");
                    return;
                }
                if (TextUtils.isEmpty(hospital) ){
                    doctorInfoUpdateDoctorHospitalEditText.setError("hospital name is required!!");
                    return;
                }
                if (TextUtils.isEmpty(availibilty) ){
                    doctorInfoUpdateDoctorAvailibilityEditText.setError("availibity is required!!");
                    return;
                }
                if (TextUtils.isEmpty(speciality) ){
                    doctorInfoUpdateSpecialityEditText.setError("specialized sector is required!!");
                    return;
                }
                if (TextUtils.isEmpty(contact) ){
                    doctorInfoUpdateContactNoEditText.setError("contact no is required!!");
                    return;
                }
                updateDoctorInfo(docID,name,hospital,availibilty,speciality,contact);

                alertDialog.dismiss();
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReferenceDoctorInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                doctorInfoListNew.clear();
                for (DataSnapshot doctorInfoSnapshotVariable : dataSnapshot.getChildren()){
                    //jotokhn prjonto list e data thakbe totokhn prjonto loop ghurbe
                    DoctorClassNew doctorInfoObj6 = doctorInfoSnapshotVariable.getValue(DoctorClassNew.class);
                    doctorInfoListNew.add(doctorInfoObj6);


                }

                AdminDoctorInfoListAdapterNew adapter = new AdminDoctorInfoListAdapterNew(AdminUpdateDoctorInfoActivity.this,
                        doctorInfoListNew);

                listViewDoctorInfo.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateDoctorInfo(String doctID,String doctName,String doctHos,
                                  String doctAvail, String doctSpecialized, String docContact){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("doctorInfoNew").child(doctID);

        DoctorClassNew doctorInfo = new DoctorClassNew(doctID,doctName,doctHos,doctAvail,doctSpecialized,docContact);

        databaseReference.setValue(doctorInfo);
        Toast.makeText(getApplicationContext(),"Doctor Info successfully updated",Toast.LENGTH_SHORT).show();



    }


}
