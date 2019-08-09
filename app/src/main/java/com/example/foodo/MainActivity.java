package com.example.foodo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment frag = null;
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    if (CurrentFragmentsSingleton.getInstance().searchState == null){
                        frag = new SearchRecipe();
                        CurrentFragmentsSingleton.getInstance().searchState = frag;
                    }
                    else {
                        frag = CurrentFragmentsSingleton.getInstance().searchState;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new SearchRecipe());

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
                        CurrentFragmentsSingleton.getInstance().myLongitude = location.getLongitude();
                        CurrentFragmentsSingleton.getInstance().myLatitude = location.getLatitude();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {
            if(grantResults.length >0 ){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            }
        }

    }
}
