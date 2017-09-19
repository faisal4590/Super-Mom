package com.faisal.supermom;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("VisibleForTests")

public class AdminAddBabyInfoActivity extends AppCompatActivity {

    EditText addInfoHeadline,addInfo;
    Button saveBabyInfo;



    //database reference
    DatabaseReference databaseBabyInfoReference;

    //Listview
    ListView listViewBabyInfo;

    List<adminBabyInfo> babyInfoListNew;

    //add image variables//

    /*Button processAddImageBtn;

    StorageReference mStorage;

    private static final int GALLERY_INTENT=2;

    private ProgressDialog mProgressDiaglog;*/

    private StorageReference mStorageRef;
    private DatabaseReference mDbRef;
    private ImageView showImageArea;
    private EditText imageNameEditText;
    private Uri imageUrl;

    Button selectImageFromGalleryBtn, processUploadImageBtn;

    public static final String FIREBASE_STORAGE_PATH="imageForNewsfeed/";
    public static final String FIREBASE_DATABASE_PATH="babyInfo/";
    public static final int REQUEST_CODE=1234;

    ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    //add image variable ends

    //next page links//
    private TextView goToUpdateNewsfeedInfoPage,goToAddDoctorInfo,goToAddNewsfeedInfo,goToUpdateDoctorInfo;

    private TextView btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_baby_info);


        //code for showing baby info//



        databaseBabyInfoReference = FirebaseDatabase.getInstance().getReference("babyInfo");
        //baby info name er new 1ta tree ke referrence hisabe nibe.. nahole root ke reference hisabe nito

        listViewBabyInfo = (ListView)findViewById(R.id.showBabyInfoListID);
        babyInfoListNew = new ArrayList<>();

        //code end for showing baby info




        addInfoHeadline = (EditText)findViewById(R.id.addInformationHeadlineID);
        addInfo = (EditText)findViewById(R.id.addInformationID);


        //saveBabyInfo = (Button)findViewById(R.id.processSaveBabyInfoBtnID);

        /*saveBabyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBabyInfo();
            }
        });*/


        firebaseAuth = FirebaseAuth.getInstance();

        btnLogOut = (TextView) findViewById(R.id.logOut);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();//finishing the current activity

                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });


        //go to next page code starts//
        goToAddDoctorInfo = (TextView)findViewById(R.id.goToAddDoctorInfoPageID);
        goToAddNewsfeedInfo = (TextView)findViewById(R.id.goToAddNewsfeedInfoPageID);
        goToUpdateNewsfeedInfoPage = (TextView)findViewById(R.id.goToUpdateNewsfeedInfoPageID);
        goToUpdateDoctorInfo = (TextView)findViewById(R.id.goToUpdateDoctorInfoID);

        goToAddDoctorInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminAddDoctorActivityNew.class));
            }
        });
        goToAddNewsfeedInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminAddBabyInfoActivity.class));
            }
        });
        goToUpdateDoctorInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminUpdateDoctorInfoActivity.class));
            }
        });

        goToUpdateNewsfeedInfoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminUpdateNewsfeedActivity.class));
            }
        });

        //go to next page code ends//


        //code for update baby info starts//

        listViewBabyInfo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                adminBabyInfo babyInfo = babyInfoListNew.get(position);
                showDialogUpdateBabyInfo(babyInfo.getBabyInfoID(),babyInfo.getBabyInfoHeadline(),babyInfo.getBabyInfo());
                return false;
            }
        });

        //code for update baby info ends//

        //code for upload image to database//

        /*mStorage = FirebaseStorage.getInstance().getReference();

        processAddImageBtn = (Button)findViewById(R.id.processUploadBabyInfoImageID);


        mProgressDiaglog = new ProgressDialog(this);*/

        mStorageRef = FirebaseStorage.getInstance().getReference();

        mDbRef = FirebaseDatabase.getInstance().getReference(FIREBASE_DATABASE_PATH);

        showImageArea = (ImageView)findViewById(R.id.adminShowImageWhenPickedID);

        imageNameEditText = (EditText)findViewById(R.id.adminGiveImageNameID);

        selectImageFromGalleryBtn = (Button)findViewById(R.id.btnBrowseImageID);

        processUploadImageBtn = (Button)findViewById(R.id.processSaveBabyInfoBtnID);

        progressDialog = new ProgressDialog(this);

        selectImageFromGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select image"),REQUEST_CODE);

            }
        });

        processUploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //addBabyInfo();
                final String headline,info;
                headline = addInfoHeadline.getText().toString();
                info = addInfo.getText().toString();


                if (imageUrl !=null){


                    progressDialog.setTitle("Uploading image...");
                    progressDialog.show();

                    StorageReference ref = mStorageRef.child(
                            FIREBASE_STORAGE_PATH +  System.currentTimeMillis() + "."+
                                    getImageName(imageUrl));

                    //add file to reference
                    ref.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(),"Image Uploaded",Toast.LENGTH_LONG).show();

                            //save image info into firebase realtime database
                            /*ForNewsfeed_UploadImageNameAndUriClass uploadImageNameAndUriClass = new
                                    ForNewsfeed_UploadImageNameAndUriClass(imageNameEditText.getText().toString()
                                    ,taskSnapshot.getDownloadUrl().toString());*/

                            String uploadID = mDbRef.push().getKey();
                            NewNewsfeedClass uploadNewsfeedInfoToFirebase = new NewNewsfeedClass(
                                    uploadID,headline,info,imageNameEditText.getText().toString(),taskSnapshot.getDownloadUrl().toString());

                           // String uploadID = mDbRef.push().getKey();
                            mDbRef.child(uploadID).setValue(uploadNewsfeedInfoToFirebase);



                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            //showing upload progress
                            double progress = (100 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();

                            progressDialog.setMessage("Uploaded " + (int)progress + "%" );


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            //dialog.dismiss();

                            Toast.makeText(getApplicationContext(),"Upload failed.. " + e.getMessage(),Toast.LENGTH_LONG).show();



                        }
                    });


                }else {
                    Toast.makeText(getApplicationContext(),"Please select an image",Toast.LENGTH_LONG).show();


                }
            }
        });


       /* goToShowListOfImagesBtn = (Button)findViewById(R.id.processShowListImageBtnID);


        processAddImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(Intent.ACTION_PICK);//jekono kisu pick korar jonno intent
                intent.setType("image*//*");//image chara ar kono datatype ami select korte chaina..
                startActivityForResult(intent,GALLERY_INTENT);


            }
        });*/

        //code for upload image to database ends

    }




    private void addBabyInfo(){
        String headline,info,imageUri,imageName;
        headline = addInfoHeadline.getText().toString();
        info = addInfo.getText().toString();
        info = addInfo.getText().toString();

        if(TextUtils.isEmpty(headline) || TextUtils.isEmpty(info)){
            Toast.makeText(getApplicationContext(),"Please fill up the field",Toast.LENGTH_SHORT).show();
        }
        else{

            String ID = databaseBabyInfoReference.push().getKey();
            adminBabyInfo babyInfoObj = new adminBabyInfo(ID,headline,info);
            databaseBabyInfoReference.child(ID).setValue(babyInfoObj);

            Toast.makeText(getApplicationContext(),"Baby Info added",Toast.LENGTH_LONG).show();



        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseBabyInfoReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                babyInfoListNew.clear();
                for (DataSnapshot babyInfoSnapshotVariable : dataSnapshot.getChildren()){
                    //jotokhn prjonto list e data thakbe totokhn prjonto loop ghurbe
                    adminBabyInfo babyInfoObj6 = babyInfoSnapshotVariable.getValue(adminBabyInfo.class);
                    babyInfoListNew.add(babyInfoObj6);


                }

                BabyInfoListAdapter adapter = new BabyInfoListAdapter(AdminAddBabyInfoActivity.this,babyInfoListNew);

                listViewBabyInfo.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showDialogUpdateBabyInfo(final String babyID, String babyHeadline, String babyInfo) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = getLayoutInflater();
        final View diaglogView = inflater.inflate(R.layout.update_dialog_for_update_baby_info,null);
        dialogBuilder.setView(diaglogView);


        final EditText babyInfoUpdateHeadlineEditText = (EditText) diaglogView.findViewById(R.id.updateBabyHeadlineID);
        final EditText babyInfoUpdateInfoEditText = (EditText) diaglogView.findViewById(R.id.updateBabyInfoID);
        Button processUpdateBabyInfo = (Button) diaglogView.findViewById(R.id.processUpdateNewsfeedInfoBtnID);


        dialogBuilder.setTitle("Updating Newsfeed");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        processUpdateBabyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String headline = babyInfoUpdateHeadlineEditText.getText().toString();
                String info = babyInfoUpdateInfoEditText.getText().toString();

                if (TextUtils.isEmpty(headline) ){
                    babyInfoUpdateHeadlineEditText.setError("headline is required!!");
                    return;
                }
                if (TextUtils.isEmpty(info) ){
                    babyInfoUpdateInfoEditText.setError("info is required!!");
                    return;
                }
                updateBabyInfoNewsfeed(babyID,headline,info);

                alertDialog.dismiss();
            }
        });




    }

    private void updateBabyInfoNewsfeed(String babyID,String babyHeadline,String babyInfovar){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("babyInfo").child(babyID);

        adminBabyInfo babyInfo = new adminBabyInfo(babyID,babyHeadline,babyInfovar);

        databaseReference.setValue(babyInfo);
        Toast.makeText(getApplicationContext(),"Newsfeed successfully updated",Toast.LENGTH_SHORT).show();



    }


    //code for upload image to database//

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK
                && data != null && data.getData() != null){

            imageUrl = data.getData();

            try{
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUrl) ;
                showImageArea.setImageBitmap(bm);

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    public  String getImageName(Uri uri){

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            mProgressDiaglog.setMessage("Uploading image...");
            mProgressDiaglog.show();

            Uri uri = data.getData();

            StorageReference filePath = mStorage.child("UploadedPhotosForNewsfeed").child(uri.getLastPathSegment());

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
*/


}
