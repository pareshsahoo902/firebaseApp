package com.demoapp.firebaseap.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demoapp.firebaseap.ChatScreen.ChatActivity;
import com.demoapp.firebaseap.MainActivity;
import com.demoapp.firebaseap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText name, phone, email, password, confirmPassword;
    Button register;
    FirebaseAuth auth;
    DatabaseReference userRef;




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

        userRef = FirebaseDatabase.getInstance().getReference().child("users");

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
                            String uid  =   auth.getCurrentUser().getUid();
                            HashMap<String,Object> userMap =new HashMap<>();
                            userMap.put("name",name.getText().toString());
                            userMap.put("phone",phone.getText().toString());
                            userMap.put("email",email.getText().toString());
                            userMap.put("id",uid);

                            userRef.child(uid).updateChildren(userMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull  Task<Void> task) {
                                            if (task.isSuccessful()){
                                                startActivity(new Intent(RegisterActivity.this, ChatActivity.class));

                                            }else{
                                                Toast.makeText(RegisterActivity.this, "Internet is not Connected!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


                           
                        }
                    }
                });

    }
}