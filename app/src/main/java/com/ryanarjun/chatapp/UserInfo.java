package com.ryanarjun.chatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInfo extends AppCompatActivity {

    private EditText etUsername, etLocaiton;
    private static final String TAG = "UserInfo";
    private String userID;

    private FirebaseDatabase mFireDatabse;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        etUsername = findViewById(R.id.etUsername);
        etLocaiton = findViewById(R.id.etlocation);
        final Button bSave = findViewById(R.id.bSave);
        mAuth = FirebaseAuth.getInstance();
        mFireDatabse = FirebaseDatabase.getInstance();
        dataRef = mFireDatabse.getReference();
        try {
            Thread.sleep(8000);
            FirebaseUser user = mAuth.getCurrentUser();
            userID = user.getUid();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    toastMessage("Successful sign in :" + user.getEmail());
                } else {
                    toastMessage("Successful sign out");
                }
            }
        };


        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // When the register button is clicked on
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String age = etLocaiton.getText().toString();
                Intent loginIntent = new Intent(UserInfo.this, Login.class);
                UserInformationData userInformationData = new UserInformationData(username, age);
                if(!username.equals("") && !age.equals("")){
                    dataRef.child("users").child(userID).setValue(userInformationData);
                    toastMessage("Fill in the data!");
                    etUsername.setText("");
                    etLocaiton.setText("");
                } else {
                    toastMessage("Fill in the data!");
                }
                UserInfo.this.startActivity(loginIntent);
                finish();
            }
        });
    }

    private void saveUserInformation() {
        String username = etUsername.getText().toString().trim();
        String location = etLocaiton.getText().toString().trim();

        // Save the information of the user
        UserInformationData userInformationData = new UserInformationData(username, location);
        dataRef.setValue(userInformationData);
        Toast.makeText(this, "Profile is updated", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
