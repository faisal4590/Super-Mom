package com.faisal.supermom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

public class AdminAddDoctorActivityNew extends AppCompatActivity {

    EditText doctorNameEditText,doctorHospitalEditText,doctorAvailibilityEditText,
    doctorSpecializedEditText,doctorContactEditText;

    Button saveDoctorInfoToFirebaseBtn;

    DatabaseReference databaseReferenceDoctorInfo;

    private TextView goToUpdateDoctorInfoPage;

    //Listview
    ListView listViewDoctorInfo;

    List<DoctorClassNew> doctorInfoListNew;


    //add image variables//

    Button processAddImageBtn;

    StorageReference mStorage;

    private static final int GALLERY_INTENT=2;

    private ProgressDialog mProgressDiaglog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_doctor_new);

        goToUpdateDoctorInfoPage = (TextView)findViewById(R.id.goToUpdateDoctorInfoID);

        goToUpdateDoctorInfoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), AdminUpdateDoctorInfoActivity.class));
            }
        });

        //database reference
        databaseReferenceDoctorInfo = FirebaseDatabase.getInstance().getReference("doctorInfoNew");

        doctorAvailibilityEditText = (EditText)findViewById(R.id.addDoctorAvailibilityID);
        doctorContactEditText = (EditText)findViewById(R.id.addDoctorContactID);
        doctorHospitalEditText = (EditText)findViewById(R.id.addDoctorHospitalID);
        doctorNameEditText = (EditText)findViewById(R.id.addDoctorNameID);
        doctorSpecializedEditText = (EditText)findViewById(R.id.addDoctorSpecializedID);

        saveDoctorInfoToFirebaseBtn = (Button)findViewById(R.id.saveDoctorInfoToFirebaseBtnID);

        saveDoctorInfoToFirebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDoctorInfo();
            }
        });


        //code for upload image to database//

        mStorage = FirebaseStorage.getInstance().getReference();

        processAddImageBtn = (Button)findViewById(R.id.processUploadImageForDoctorID);


        mProgressDiaglog = new ProgressDialog(this);


        processAddImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(Intent.ACTION_PICK);//jekono kisu pick korar jonno intent
                intent.setType("image/*");//image chara ar kono datatype ami select korte chaina..
                startActivityForResult(intent,GALLERY_INTENT);


            }
        });

        //code for upload image to database ends




    }

    private void addDoctorInfo(){
        String doctorNameStr,doctorHospitalStr,
                doctorAvalibilityStr,doctorSpecializedStr,doctorContactStr,doctorIDStr;

        doctorNameStr = doctorNameEditText.getText().toString();
        doctorHospitalStr = doctorHospitalEditText.getText().toString();
        doctorAvalibilityStr = doctorAvailibilityEditText.getText().toString();
        doctorSpecializedStr = doctorSpecializedEditText.getText().toString();
        doctorContactStr = doctorContactEditText.getText().toString();

        if(TextUtils.isEmpty(doctorAvalibilityStr) || TextUtils.isEmpty(doctorContactStr) ||
                TextUtils.isEmpty(doctorHospitalStr) ||
                TextUtils.isEmpty(doctorNameStr) ||
                TextUtils.isEmpty(doctorSpecializedStr) ){
            Toast.makeText(getApplicationContext(),"Please fill up the field",Toast.LENGTH_SHORT).show();
        }

        else{
            String doctorIDVarNew = databaseReferenceDoctorInfo.push().getKey();

            DoctorClassNew doctorInfoObjNew = new DoctorClassNew(doctorIDVarNew,doctorNameStr,
                    doctorHospitalStr,doctorSpecializedStr,doctorAvalibilityStr,doctorContactStr);

            databaseReferenceDoctorInfo.child(doctorIDVarNew).setValue(doctorInfoObjNew);

            Toast.makeText(getApplicationContext(),"Doctor Info added",Toast.LENGTH_LONG).show();

        }
    }


    //code for upload image to database//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            mProgressDiaglog.setMessage("Uploading image...");
            mProgressDiaglog.show();

            Uri uri = data.getData();

            StorageReference filePath = mStorage.child("UploadedPhotosForDoctors").child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //image thikmoto upload hole then ei code execute hobe

                    Toast.makeText(getApplicationContext(),"Image uploaded",Toast.LENGTH_SHORT).show();
                    mProgressDiaglog.dismiss();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Image upload failed",Toast.LENGTH_SHORT).show();
                    mProgressDiaglog.dismiss();
                }
            });

        }

    }



}
