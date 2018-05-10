package com.ryanarjun.chatapp;

/**
 * Created by ryanarjun on 3/26/18.
 */

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;

public class ChatAppAdapter extends RecyclerView.Adapter<ChatAppAdapter.ChatAppViewHolder> {


    private FirebaseAuth mAuth;
    private List<ChatMessage> mMessageList;
    public static String r;
    public static String g;
    public static String b;

    public ChatAppAdapter(List<ChatMessage> mMessageList) {
        this.mMessageList = mMessageList;
    }

    @Override
    public ChatAppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mAuth = FirebaseAuth.getInstance();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.singlemessagelayout ,parent, false);

        return new ChatAppViewHolder(v);

    }

    public class ChatAppViewHolder extends RecyclerView.ViewHolder {

        public TextView message_content;
        public TextView name_content;
        public TextView time_content;

        public ChatAppViewHolder(View view) {
            super(view);
            message_content = view.findViewById(R.id.messageTextT);
            name_content = view.findViewById(R.id.usernameText);
            time_content = view.findViewById(R.id.timeStamp);

        }
    }

    @Override
    public void onBindViewHolder(final ChatAppViewHolder viewHolder, int i) {

        mAuth = FirebaseAuth.getInstance();

        String currentUser = mAuth.getCurrentUser().getUid();

        ChatMessage c = mMessageList.get(i);

        String from = c.getFrom();
        int r_color = Integer.parseInt(r);
        int b_color = Integer.parseInt(b);
        int g_color = Integer.parseInt(g);


        if(from.equals(currentUser)){
            viewHolder.message_content.setTextColor(Color.rgb(r_color, g_color, b_color));
            viewHolder.name_content.setTextColor(Color.rgb(r_color, g_color, b_color));
            viewHolder.time_content.setTextColor(Color.rgb(r_color, g_color, b_color));

        } else {
            viewHolder.message_content.setTextColor(Color.rgb(160, 144, 20));
            viewHolder.name_content.setTextColor(Color.rgb(160, 144, 20));
            viewHolder.time_content.setTextColor(Color.rgb(160, 144, 20));
        }
        viewHolder.message_content.setText(c.getMessage());
        viewHolder.name_content.setText(c.getName());
        viewHolder.time_content.setText(c.getTime());

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

}

