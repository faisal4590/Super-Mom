package com.faisal.supermom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class RemainderActivity extends AppCompatActivity {

    //next page variables//

    private TextView beAnAweosmeMomPage,goTohealthMonitor,goToOurSpecialistPage,
            goToAboutUsPage,goToRemainderPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder);




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

}
