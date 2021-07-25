package com.demoapp.firebaseap.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.demoapp.firebaseap.MainActivity;
import com.demoapp.firebaseap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText  email, password;
    Button login;
    TextView reg;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        FirebaseApp.initializeApp(getApplicationContext());
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

        }

        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        login = findViewById(R.id.login);
        reg = findViewById(R.id.reg);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "No passcode", Toast.LENGTH_SHORT).show();
                }else{
                    loginUser();
                }
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });
    }

    private void loginUser() {
        auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Failed!"+task.getException(), Toast.LENGTH_SHORT).show();
                        }else{

                            //Save name and phone number with email in the DB if registered

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    }
                });
    }
}