package com.ryanarjun.chatapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainMenu extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;


    // The is the user profile screen of the app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        // initializing firebase
        Firebase.setAndroidContext(this);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();


        // Set the fields and buttons
        final Button bLogout = findViewById(R.id.bLogout);
        final Button bChatroom = findViewById(R.id.bChatroom);
        final Button bEditProfile = findViewById(R.id.bEditProfile);
        final Button bUsers = findViewById(R.id.bUsers);

        if (user == null){
            finish();
            startActivity(new Intent(this, Login.class));
        }

        // Saves the data the user put in  the text fields

        // If the logout button is clicked on create an intent of the login screen
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent loginIntent = new Intent(MainMenu.this, Login.class);
                MainMenu.this.startActivity(loginIntent);
                finish();
            }
        });

        // Launch the chatroom page
        bChatroom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent chatroomIntent = new Intent(MainMenu.this,
                        AddCategory.class);
                MainMenu.this.startActivity(chatroomIntent);
                finish();
            }
        });

        bEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EditProfileIntent = new Intent(MainMenu.this,
                        ActivityEditProfile.class);
                MainMenu.this.startActivity(EditProfileIntent);
                finish();
            }
        });

        bUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent UsersIntent = new Intent(MainMenu.this,
                        UserList.class);
                MainMenu.this.startActivity(UsersIntent);
                finish();
            }
        });

    }


}