package com.ryanarjun.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ActivityEditProfile extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    public static String r;
    public static String g;
    public static String b;
    private String userID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;
    private DatabaseReference ChatDB;
    private EditText etUsername;
    private FirebaseDatabase mFireDatabse;
    private DatabaseReference dataRef;
    public static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        etUsername = findViewById(R.id.etEditUsername);
        mFireDatabse = FirebaseDatabase.getInstance();
        dataRef = mFireDatabse.getReference();

        final Button bSave = findViewById(R.id.bsaveEditProfile);
        final Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> colorAdpater = new ArrayAdapter<String>(
                ActivityEditProfile.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.Colors));
        colorAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(colorAdpater);

        dataRef.child("users").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("username").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EditInfoIntent = new Intent(ActivityEditProfile.this, MainMenu.class);
                final String color = spinner.getSelectedItem().toString();
                String username = etUsername.getText().toString();
                UserInformationData userInformationData = new UserInformationData(username, null);

                dataRef.child("users").child(userID).setValue(userInformationData);

                if(color.equals("Black")){
                    r="0";
                    b="0";
                    g="0";
                } else if(color.equals("Red")){
                    r="170";
                    b="10";
                    g="10";
                } else if(color.equals("Blue")){
                    r="7";
                    b="191";
                    g="7";
                } else if(color.equals("Green")){
                    r="4";
                    b="46";
                    g="142";
                } else if(color.equals("Gold")){
                    r="150";
                    b="1";
                    g="147";
                }
                Color color1 = new Color(r, g, b);

                mDatabase.child("color").child(userID).setValue(color1);
                ActivityEditProfile.this.startActivity(EditInfoIntent);
                finish();
            }
        });


        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id  = item.getItemId();

        if(id==android.R.id.home){
            this.finish();
            startActivity(new Intent(this, MainMenu.class));
        }
        return super.onOptionsItemSelected(item);
    }




}
