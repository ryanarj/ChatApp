package com.ryanarjun.chatapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AddCategory extends AppCompatActivity {

    DatabaseReference reference;
    ArrayList<String> arrayList;
    EditText e1;
    ListView l1;
    ArrayAdapter<String> adapter;
    UserList userList;
    public static String chatroom;
    public static boolean isChatroom = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        e1 = findViewById(R.id.editText);
        l1 = findViewById(R.id.listView);
        arrayList = new ArrayList<>();

        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);
        l1.setAdapter(adapter);


        reference = FirebaseDatabase.getInstance().getReference().child("chat").child("chatrooms");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<>();


                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());

                }

                arrayList.clear();
                arrayList.addAll(set);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddCategory.this, "No network connectivity", Toast.LENGTH_SHORT).show();
            }
        });


        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent(AddCategory.this, MainChatroomNew.class);
                intent.putExtra("room_name", ((TextView) view).getText().toString());
                chatroom = (((TextView) view).getText().toString());
                isChatroom = true;
                userList.isChatroomFriend = false;
                startActivity(intent);

            }
        });

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


    public void insert_data(View v) {
        Map<String,Object> mapChatrooms = new HashMap<>();
        mapChatrooms.put(e1.getText().toString(), "");
        reference.updateChildren(mapChatrooms);
    }

}

