package com.example.foodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    public static FragmentManager fragmentManager;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // add back arrow on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getSupportFragmentManager();
                if(findViewById(R.id.fragment_container) != null){
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    SearchRecipe nextFragment = new SearchRecipe();
                    fragmentTransaction.add(R.id.fragment_container, nextFragment, null);
                    fragmentTransaction.commit();
                }
            }
        });




    }
}
