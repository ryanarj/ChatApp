package com.ryanarjun.chatapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class MainChatroom extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private String name;
    ActivityEditProfile activityEditProfile;
    private DatabaseReference dataRef;
    private FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chatroom);
        firebaseDatabase = FirebaseDatabase.getInstance();
        dataRef = firebaseDatabase.getReference();
        Button bSend = findViewById(R.id.bSend);
        final EditText etMessage = findViewById(R.id.etMessage);
        ListView lvMessageList = findViewById(R.id.lvMessageList);
        Firebase.setAndroidContext(this);
        final Firebase ref = new Firebase("https://chatapp-eb1c8.firebaseio.com//chat");
        final Firebase userRef = new Firebase("https://chatapp-eb1c8.firebaseio.com//users");

        // initializing firebase
        firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getCurrentUser().getUid();

        // Get the name of the user
        userRef.child(userID).addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("username").getValue().toString();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat df = new SimpleDateFormat("h:mm a");
                String time = df.format(Calendar.getInstance().getTime());
                ChatMessage chat = new ChatMessage(name, etMessage.getText().toString(), time);
                ref.push().setValue(chat);
                etMessage.setText("");
            }
        });

        final List<ChatMessage> messages = new LinkedList<>();
        final ArrayAdapter<ChatMessage> adapter = new ArrayAdapter<ChatMessage>(
                this, android.R.layout.two_line_list_item, messages){

            @Override
            public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
                if (view == null){
                    view = getLayoutInflater().inflate(android.R.layout.two_line_list_item, parent, false);
                }
                ChatMessage chat = messages.get(position);
                ((TextView)view.findViewById(android.R.id.text1)).setText(chat.getName());
                ((TextView)view.findViewById(android.R.id.text2)).setText(chat.getMessage());
                return view;
            }
        };
        lvMessageList.setAdapter(adapter);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMessage chat = dataSnapshot.getValue(ChatMessage.class);
                messages.add(chat);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
    }
}