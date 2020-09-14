package com.example.sellitpt5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout email_input, password_input;
    Button sign_in_button;
    TextView transfer_to_signup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email_input = findViewById(R.id.email_input_login_main);
        password_input = findViewById(R.id.password_input_login_main);
        sign_in_button = findViewById(R.id.sign_in_button_main);
        mAuth = FirebaseAuth.getInstance();
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_in_user();
            }
        });
        transfer_to_signup = findViewById(R.id.sign_up_transfer);
        transfer_to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( LoginActivity.this, RegisterActivity.class));
            }
        });



    }

    private void sign_in_user(){
        String email = email_input.getEditText().getText().toString();
        String password = password_input.getEditText().getText().toString();
        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                startActivity( new Intent(LoginActivity.this, MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Invalid email or password",Toast.LENGTH_LONG ).show();

            }
        });

    }


}
