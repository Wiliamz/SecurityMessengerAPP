package com.mydogenterprises.securitymessenger.ui.contacts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mydogenterprises.securitymessenger.R;
import com.mydogenterprises.securitymessenger.data.model.User;
import com.mydogenterprises.securitymessenger.ui.chat.ChatActivity;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private GroupAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        RecyclerView rv = findViewById(R.id.recycler);
        adapter = new GroupAdapter<>();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(ContactsActivity.this, ChatActivity.class);

                UserItem userItem = (UserItem) item;
                intent.putExtra("user",userItem.user);

                startActivity(intent);
            }
        });
        fetchUsers();
    }

    private void fetchUsers() {
        FirebaseFirestore.getInstance().collection("/users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.e("teste",e.getMessage(),e);
                }

                List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot doc: docs) {
                    User user = doc.toObject(User.class);
                    adapter.add(new UserItem(user));
                    Log.d("Teste", user.getUsername());

                }
            }
        });
    }

    private class UserItem extends Item<GroupieViewHolder> {

        private final User user;

        private UserItem(User user) {
            this.user = user;
        }


        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            TextView nomeContato = viewHolder.itemView.findViewById(R.id.nomeContato);
            ImageView photo = viewHolder.itemView.findViewById(R.id.photo);

            nomeContato.setText(user.getUsername());
            Picasso.get().load(user.getProfileUrl()).into(photo);

        }

        @Override
        public int getLayout() {
            return R.layout.item_user;
        }
    }
}
