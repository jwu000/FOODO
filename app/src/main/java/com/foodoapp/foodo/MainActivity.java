package com.foodoapp.foodo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * main activity that swaps in and out all the fragments of different parts of app
 */
public class MainActivity extends AppCompatActivity {


    BottomNavigationView navView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        // changes fragment container based on what is selected on bottom nav bar
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment frag = null;
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    if (CurrentSessionInfoSingleton.getInstance().searchState == null){
                        frag = new SearchRecipe();
                        CurrentSessionInfoSingleton.getInstance().searchState = frag;
                    }
                    else {
                        frag = CurrentSessionInfoSingleton.getInstance().searchState;
                    }
                    break;
                case R.id.navigation_favorite:
                    frag = new Favorites();
                    break;
                case R.id.navigation_history:
                    frag = new History();
                    break;
                case R.id.navigation_settings:
                    frag = new Settings();
                    break;
            }
            return loadFragment(frag);
        }
    };

    // changes fragment in hte fragment container
    private Boolean loadFragment(Fragment frag){
        if (frag != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,frag)
                    .commit();
            return true;
        }
        else {
            return false;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //add back_arrow on the toolbar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new SearchRecipe());




        // ask for user location permission until they give
        if ( ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  Manifest.permission.ACCESS_FINE_LOCATION  },
                    0);

        }

        if ( ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED )
        {
            final LocationManager locationMananger = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);


            locationMananger.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        Log.d("Location Changed", location.getLatitude() + " and " + location.getLongitude());
                        //locationMananger.removeUpdates(this);
                        CurrentSessionInfoSingleton.getInstance().myLongitude = location.getLongitude();
                        CurrentSessionInfoSingleton.getInstance().myLatitude = location.getLatitude();
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_bar,menu);
        return true;
    }

    // if menu bar restart button pressed, restart the search compare process
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_restart) {
            Fragment nextFragment = new SearchRecipe();
            CurrentSessionInfoSingleton.getInstance().searchState = nextFragment;
            navView = findViewById(R.id.nav_view);
            navView.getMenu().getItem(0).setChecked(true);
            return loadFragment(nextFragment);

        }
        else {
            return false;
        }
    }

    // checks if user gave locaiton permission if not then keep asking
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {
            if(grantResults.length >0 ){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
                else {
                    ActivityCompat.requestPermissions( this, new String[] {  Manifest.permission.ACCESS_FINE_LOCATION  },0);
                }
            }

        }

    }

    // back arrow functionality
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() == 0) {
            return;
        }
        super.onBackPressed();
    }
}
