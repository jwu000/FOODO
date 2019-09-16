package com.foodoapp.foodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

/**
 *  activity for log in screen. also allows user to create account here
 */
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
                if (emailData == null || passwordData == null || emailData.equals("") || passwordData.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill in both email and password", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    logIn = new Intent(LoginActivity.this, MainActivity.class);
                    authenticationRef.signInWithEmailAndPassword(emailData, passwordData).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String[] userDataSplit = emailData.split("@");
                                CurrentSessionInfoSingleton.getInstance().user = userDataSplit[0];
                                startActivity(logIn);
                            } else {
                                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailData = email.getText().toString();
                passwordData = password.getText().toString();
                if (emailData == null || passwordData == null || emailData.equals("") || passwordData.equals("")){
                    Toast.makeText(getApplicationContext(), "Please fill in both email and password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    logIn = new Intent(LoginActivity.this, MainActivity.class);
                    authenticationRef.createUserWithEmailAndPassword(emailData, passwordData).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String[] userDataSplit = emailData.split("@");
                                CurrentSessionInfoSingleton.getInstance().user = userDataSplit[0];
                                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                startActivity(logIn);
                            }else{
                                Toast.makeText(getApplicationContext(), "Register Fail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }
}
