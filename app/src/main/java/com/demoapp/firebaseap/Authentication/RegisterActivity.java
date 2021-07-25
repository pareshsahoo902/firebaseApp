package com.demoapp.firebaseap.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demoapp.firebaseap.MainActivity;
import com.demoapp.firebaseap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText name, phone, email, password, confirmPassword;
    Button register;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.Confirm_password);
        register = findViewById(R.id.register);
        auth = FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals("") || !password.getText().toString().equals(confirmPassword.getText().toString())) {

                    Toast.makeText(RegisterActivity.this, "NO match password", Toast.LENGTH_SHORT).show();
                    return;
                }
                registerUser();
            }
        });
    }

    private void registerUser() {
        auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Failed!"+task.getException(), Toast.LENGTH_SHORT).show();
                        }else{

                            //Save name and phone number with email in the DB if registered

                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        }
                    }
                });

    }
}