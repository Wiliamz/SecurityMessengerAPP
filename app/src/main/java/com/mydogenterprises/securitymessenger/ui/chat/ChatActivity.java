package com.mydogenterprises.securitymessenger.ui.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mydogenterprises.securitymessenger.R;
import com.mydogenterprises.securitymessenger.data.model.User;
import com.mydogenterprises.securitymessenger.ui.messages.MessagesActivity;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

public class ChatActivity extends AppCompatActivity {

    private GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        User user = getIntent().getExtras().getParcelable("user");

        assert user != null;

        getSupportActionBar().setTitle(user.getUsername());

        RecyclerView rv = findViewById(R.id.recyler_chat);

        adapter = new GroupAdapter<>();

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        adapter.add(new MessageItem(true));
        adapter.add(new MessageItem(false));
        adapter.add(new MessageItem(false));
        adapter.add(new MessageItem(true));
        adapter.add(new MessageItem(true));
        adapter.add(new MessageItem(false));
        adapter.add(new MessageItem(true));
        adapter.add(new MessageItem(false));
    }

    private class MessageItem extends Item<GroupieViewHolder>{

        private boolean isLeft;

        private MessageItem(boolean isLeft){
            this.isLeft = isLeft;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {

        }

        @Override
        public int getLayout() {
            return isLeft ? R.layout.item_from_message : R.layout.item_to_message;
        }
    }
}
