package com.example.sellitpt5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private TextInputLayout phone_inp, email_inp, pass_inp, name_inp;
    private Button register_button;
    private FirebaseAuth mAuth;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        phone_inp = findViewById(R.id.phone_number_input_login);
        email_inp = findViewById(R.id.email_input_login);
        pass_inp = findViewById(R.id.password_input_login);
        name_inp = findViewById(R.id.name_input_login);
        register_button = findViewById(R.id.register_button);
        mAuth = FirebaseAuth.getInstance();
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });


    }


    private void addUserData(final String user_id, String email){

        final String phone_number = phone_inp.getEditText().getText().toString();
        final String name = name_inp.getEditText().getText().toString();
        DataStorage.getUser_data_collection().document(user_id).set( new UserDataSchema(name, email, phone_number, new ArrayList<String>()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RegisterActivity.this, "Done Adding database info", Toast.LENGTH_LONG);
                        DataStorage.setCurrent_user_phone(phone_number);

                    }
                });
    }
    private void registerUser(){
        String email = email_inp.getEditText().getText().toString();
        String password = pass_inp.getEditText().getText().toString();
        mAuth.createUserWithEmailAndPassword( email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser current_user = authResult.getUser();
                addUserData( current_user.getUid(),current_user.getEmail() );

                startActivity(new Intent( RegisterActivity.this, MainActivity.class ));
                


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, e.toString());
            }
        });


    }
}
