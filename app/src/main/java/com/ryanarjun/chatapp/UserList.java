package com.ryanarjun.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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

public class UserList extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    ArrayList<String> arrayList;
    public static ArrayList<String> arrayList2;
    ArrayAdapter<String> adapter;
    ListView l1;
    String userID;
    AddCategory addCategory;
    public static String chatroomFriend;
    public static boolean isChatroomFriend = false;
    public static String chatroomFriendUID;

    final ArrayList<UserInformationData> userInformationDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        l1 = findViewById(R.id.lvUserList);
        final Map<String, String> map = new HashMap<>();
        arrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);
        l1.setAdapter(adapter);
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<>();


                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    DataSnapshot ds = (DataSnapshot) i.next();
                    set.add(ds.child("username").getValue().toString());
                    map.put(ds.child("username").getValue().toString(),  ds.getKey().toString());
                }

                arrayList.clear();
                arrayList.addAll(set);
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserList.this, "No network connectivity", Toast.LENGTH_SHORT).show();
            }
        });



        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent(UserList.this, MainChatroomNew.class);
                intent.putExtra("room_name", ((TextView) view).getText().toString());
                chatroomFriend = (((TextView) view).getText().toString());
                isChatroomFriend = true;
                addCategory.isChatroom = false;
                chatroomFriendUID = map.get(chatroomFriend);
                startActivity(intent);

            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("chat").child("privateChat");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());
                }
                arrayList2.clear();
                arrayList2.addAll(set);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserList.this, "No network connectivity", Toast.LENGTH_SHORT).show();
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

