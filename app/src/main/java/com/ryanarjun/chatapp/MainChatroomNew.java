package com.ryanarjun.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainChatroomNew extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText editMessage;
    private RecyclerView mMessageList;
    private DatabaseReference mDatabase;
    private DatabaseReference MsgDbUser;
    private DatabaseReference mRootRef;
    private DatabaseReference UserDB;
    private DatabaseReference colorDB;
    private String userID;
    public static String name;
    ChatAppAdapter chatAppAdapterColor;
    ArrayList<String> privatechatlist;
    AddCategory addCategory;
    UserList userList;
    String privateChatString1;
    String privateChatString2;
    String privatechat;

    private final List<ChatMessage> messageList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private ChatAppAdapter chatAppAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chatroom_new);
        editMessage = findViewById(R.id.editMessageE);
        mMessageList = findViewById(R.id.messageRec);
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(
                "https://chatapp-eb1c8.firebaseio.com/");
        UserDB = mDatabase.child("users");
        privatechatlist = userList.arrayList2;
        mRootRef = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        privateChatString1 = userID + userList.chatroomFriendUID;
        privateChatString2 = userList.chatroomFriendUID + userID;
        privatechat = privateChatString1;
        linearLayoutManager = new LinearLayoutManager(this);
        chatAppAdapter = new ChatAppAdapter(messageList);
        mMessageList.setHasFixedSize(true);
        mMessageList.setLayoutManager(linearLayoutManager);
        mMessageList.setAdapter(chatAppAdapter);


        if (userList.isChatroomFriend) {
            MsgDbUser = mDatabase.child("chat").child("privateChat");
            System.out.println("HELLOOOOO Private");
        } else if (addCategory.isChatroom) {
            MsgDbUser = mDatabase.child("chat").child("chatrooms").child(addCategory.chatroom);
            System.out.println("HELLOOOOO ChatRoom");
        }


        loadMessages();

        if (!addCategory.isChatroom){
            Iterator<String> itr = privatechatlist.iterator();
            while (itr.hasNext()) {
                String element = itr.next();
                if (element.equals(privateChatString1)) {
                    privatechat = privateChatString1;
                    break;
                } else if (element.equals(privateChatString2)) {
                    privatechat = privateChatString2;
                    break;
                }
            }
        }

        // Get the linear layout
//        mMessageList = findViewById(R.id.messageRec);
//        mMessageList.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setStackFromEnd(true);

        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Get Username

        // Get the name of the user
        UserDB.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("username").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        colorDB = mDatabase.child("color").child(userID);


        colorDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatAppAdapterColor.r = dataSnapshot.child("r").getValue().toString();
                chatAppAdapterColor.g = dataSnapshot.child("g").getValue().toString();
                chatAppAdapterColor.b = dataSnapshot.child("b").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    public void sendButtonClicked(View view){

        sendMessage();
        editMessage.getText().clear();
    }

    private void loadMessages(){
        if(!addCategory.isChatroom)
            MsgDbUser.child(userList.chatroomFriendUID).child(userID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                messageList.add(chatMessage);
                chatAppAdapter.notifyDataSetChanged();

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
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        else{
            MsgDbUser.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    messageList.add(chatMessage);
                    chatAppAdapter.notifyDataSetChanged();

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
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

//    @Override
//    protected void onStart(){
//        super.onStart();
//        FirebaseRecyclerAdapter<Message, MessageViewHolder> FBRAUSER = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(
//
//                Message.class,
//                R.layout.singlemessagelayout,
//                MessageViewHolder.class,
//                MsgDbUser
//
//        ) {
//
//            @Override
//            protected void populateViewHolder(MessageViewHolder viewHolder, Message model, int position) {
//                if(model.getName() != null && model.getMessage() != null) {
//                    viewHolder.setMessage(model.getMessage());
//                    viewHolder.setName(model.getName());
//                    viewHolder.setTime(model.getTime());
//                }
//            }
//
//        };
//        mMessageList.setLayoutManager(new LinearLayoutManager(this));
//        mMessageList.setAdapter(FBRAUSER);
//    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView message_content;
        TextView name_content;
        TextView time_content;
        public MessageViewHolder(View itemView){
            super(itemView);
            mView = itemView;
            message_content = mView.findViewById(R.id.messageTextT);
        }

        public void setMessage(String message){
            message_content = mView.findViewById(R.id.messageTextT);
            message_content.setText(message);
        }

        public void setName(String name){
            name_content = mView.findViewById(R.id.usernameText);
            name_content.setText(name);
        }

        public void setTime(String time){
            time_content = mView.findViewById(R.id.timeStamp);
            time_content.setText(time);
        }
    }

    private void sendMessage() {

        DateFormat df = new SimpleDateFormat("h:mm a M/d/y" );
        String time = df.format(Calendar.getInstance().getTime());
        String current_user_ref;
        String chat_user_ref;
        DatabaseReference user_message_push;


        final String messageValue = editMessage.getText().toString().trim();

        if(!TextUtils.isEmpty(messageValue)){
            if(!addCategory.isChatroom) {
                current_user_ref = "chat/privateChat/" +  userID + "/" + userList.chatroomFriendUID;
                chat_user_ref = "chat/privateChat/" + userList.chatroomFriendUID + "/" + userID;
                user_message_push = mRootRef.child("chat").child("privateChat")
                        .child(userID).child(userList.chatroomFriendUID).push();
            } else {
                current_user_ref = "chat/chatrooms/" + addCategory.chatroom;
                chat_user_ref = "chat/chatrooms/" + addCategory.chatroom;
                user_message_push = mRootRef.child("chat").child("chatrooms")
                        .child(addCategory.chatroom).push();
            }

            String push_id = user_message_push.getKey();

            Map messageMap = new HashMap();
            messageMap.put("name", name);
            messageMap.put("message", messageValue);
            messageMap.put("time", time);
            messageMap.put("from", userID);

            Map messageUserMap = new HashMap();
            messageUserMap.put(current_user_ref + "/" + push_id, messageMap);
            messageUserMap.put(chat_user_ref + "/" + push_id, messageMap);

            editMessage.setText("");
            mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    if(databaseError != null){

                        Log.d("CHAT_LOG", databaseError.getMessage().toString());

                    }

                }
            });

        }

    }
}

