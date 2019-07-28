package com.example.foodo;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


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
    }

}
