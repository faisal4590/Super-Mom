package com.faisal.supermom;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class AboutUsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    GoogleMap map;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        //  map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, (android.location.LocationListener) this);
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
                            Toast.makeText(AboutUsActivity.this,
                                    "You have been signed out.",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // Close activity
                            startActivity(new Intent(AboutUsActivity.this,LoginActivity.class));

                        }
                    });
        }

        if (item.getItemId() == R.id.newsfeedMenuNewID){
            startActivity(new Intent(AboutUsActivity.this,NewNewsfeedActivity.class));
        }

        if (item.getItemId() == R.id.healthMonitorMenuNewID){
            startActivity(new Intent(AboutUsActivity.this,HealthMonitorActivity.class));
        }

        if (item.getItemId() == R.id.remainderMenuNewID){
            startActivity(new Intent(AboutUsActivity.this,RemainderActivity.class));
        }

        if (item.getItemId() == R.id.ourSpecialistsMenuNewID){
            startActivity(new Intent(AboutUsActivity.this,OurSpecialistActivity.class));
        }

        if (item.getItemId() == R.id.forumMenuNewID){
            startActivity(new Intent(AboutUsActivity.this,ForumActivity.class));
        }

        if (item.getItemId() == R.id.aboutUsMenuNewID){
            startActivity(new Intent(AboutUsActivity.this,AboutUsActivity.class));
        }

        return true;
    }

    //Navigation menu code ends here//



    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;


        // map.setMyLocationEnabled(true);


    }

    @Override
    public void onLocationChanged(Location location) {

        map.clear();
        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocation);
        markerOptions.title("i'm here");

        map.addMarker(markerOptions);

        // map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 17.0f));

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 17.0f));
    }


}
