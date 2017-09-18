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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import static android.R.attr.value;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signUpBtn;
    private EditText email, password,username,confirmPassword;
    private TextView alreadyAccount;

    private ProgressDialog  progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);//each time a user signs up, we need a progress dialog. thats why we defined the object here

        firebaseAuth = FirebaseAuth.getInstance();//initializing firebase

        if (firebaseAuth.getCurrentUser() !=null){
            //checking whether the user is currently logged in or not
            finish();
            startActivity(new Intent(getApplicationContext(),HomePageActivity.class));
        }



        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        email = (EditText) findViewById(R.id.emailID);
        password = (EditText) findViewById(R.id.passwordID);
        username= (EditText)findViewById(R.id.username);
        confirmPassword = (EditText) findViewById(R.id.confirmPasswordID);

        alreadyAccount = (TextView) findViewById(R.id.alreadyAccount);

        signUpBtn.setOnClickListener(this);//same class er moddhei.. tai this pathalam
        alreadyAccount.setOnClickListener(this);

    }



    private void registerUser() {

        String emailStr = email.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();
        String confirmPasswordStr = confirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(emailStr)) {
            //checking wheather the email is empty or not
            Toast.makeText(this, "please enter a email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(passwordStr)) {
            //checking wheather the password is empty or not
            Toast.makeText(this, "please enter your password", Toast.LENGTH_LONG).show();
            return;

        }

        if (passwordStr.length() < 5) {
            //length must be greater than 5
            Toast.makeText(this, "password must be greater than 5 characters", Toast.LENGTH_LONG).show();
            return;

        }

        if (!passwordStr.equals(confirmPasswordStr)){
            Toast.makeText(this, "password did not match", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registering user..");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(emailStr,passwordStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            //registration korar por ja korbo
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //registration is successful
                    //redirect to login page
                    finish();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                }
                else if (task.getException() instanceof FirebaseAuthUserCollisionException){
                    Toast.makeText(MainActivity.this, "Email already exists!", Toast.LENGTH_LONG).show();
                    progressDialog.hide();
                    return;
                }
                else{
                    Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                    myIntent.putExtra("key", value); //Optional parameters
                    startActivity(myIntent);

                    Toast.makeText(MainActivity.this,"Registration failed, please try again",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view == signUpBtn) {
            registerUser();
        }
        if (view == alreadyAccount) {
            //will open login activity
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}
