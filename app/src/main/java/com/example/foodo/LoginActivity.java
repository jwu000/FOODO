package com.example.foodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton, signupButton;
    private EditText email, password;
    private String emailData, passwordData;
    private FirebaseAuth authenticationRef;

    private Intent logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        signupButton = findViewById(R.id.signup_button);
        authenticationRef = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  emailData = email.getText().toString();
                  passwordData = password.getText().toString();
                  logIn = new Intent(LoginActivity.this, MainActivity.class);
                  authenticationRef.signInWithEmailAndPassword(emailData, passwordData).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if(task.isSuccessful()){
                              startActivity(logIn);
                          } else {
                              Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                          }
                      }
                  });
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent goToSignUp = new Intent(LoginActivity.this, SignUp.class);
                startActivity(goToSignUp);
            }
        });

    }
}
