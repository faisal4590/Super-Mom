package com.faisal.supermom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSignIn;
    private EditText emailSignIn, passwordSignIn;
    private TextView dontHaveAccount;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();


        btnSignIn = (Button) findViewById(R.id.signInBtn);
        emailSignIn = (EditText) findViewById(R.id.emailIDSignIn);
        passwordSignIn = (EditText) findViewById(R.id.passwordIDSignIn);
        dontHaveAccount = (TextView) findViewById(R.id.noAccount);


        progressDialog = new ProgressDialog(this);

        if (firebaseAuth.getCurrentUser() !=null){
            //checking whether the user is currently logged in or not
            finish();
            startActivity(new Intent(getApplicationContext(),HomePageActivity.class));
        }


        btnSignIn.setOnClickListener(this);
        dontHaveAccount.setOnClickListener(this);

    }

    private void userSignIn() {
        final String email = emailSignIn.getText().toString().trim();
        String password = passwordSignIn.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //checking wheather the email is empty or not
            Toast.makeText(this, "please enter a email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //checking wheather the password is empty or not
            Toast.makeText(this, "please enter your password", Toast.LENGTH_LONG).show();
            return;

        }

        if (password.length() < 5) {
            //length must be greater than 5
            Toast.makeText(this, "password must be greater than 5 characters", Toast.LENGTH_LONG).show();
            return;

        }

        progressDialog.setMessage("Loggin in..");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            //starting the profile page/Home page
                            finish();
                            FirebaseUser user = firebaseAuth.getCurrentUser();//getting the user info


                            if(email.equalsIgnoreCase("admin@gmail.com")){
                                startActivity(new Intent(getApplicationContext(),AdminAddBabyInfoActivity.class));
                            }
                            else{
                                //Toast.makeText(LoginActivity.this, "Username is : "+ user.getEmail(), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),HomePageActivity.class));
                            }


                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Username and password did not match", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }

    @Override
    public void onClick(View view) {
        if (view == btnSignIn) {
            userSignIn();
        }
        if (view == dontHaveAccount) {
            finish();
            startActivity(new Intent(this, MainActivity.class));

        }
    }
}
