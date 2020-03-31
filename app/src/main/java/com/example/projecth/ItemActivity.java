package com.example.projecth;

import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projecth.ui.Main3Activity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ItemActivity extends AppCompatActivity {

    private RecyclerView mPeopleRV;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Item, ItemViewHolder> mPeopleRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Item");
        mDatabase.keepSynced(true);

        mPeopleRV = (RecyclerView) findViewById(R.id.myRecycleView);

        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child("Item");
        Query personsQuery = personsRef.orderByKey();

        mPeopleRV.hasFixedSize();
        mPeopleRV.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<Item>().setQuery(personsQuery, Item.class).build();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mPeopleRV.setLayoutManager(gridLayoutManager);
        mPeopleRVAdapter = new FirebaseRecyclerAdapter<Item, ItemViewHolder>(personsOptions) {
            @Override
            protected void onBindViewHolder(ItemViewHolder holder, final int position, final Item model) {
                holder.setTitle(model.getTitle());
                holder.setImage(getBaseContext(), model.getImage());
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String url = model.getPrc();
                        final String nameof = model.getTitle();
                        final String imgurl = model.getImage();
                        final String kid = model.getDesc();
//                        Intent intent = new Intent(getApplicationContext(), ItemWebView.class);
                        Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
                        intent.putExtra("id", url);
                        intent.putExtra("prodtitle",nameof);
                        intent.putExtra("imgurl",imgurl);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_row, parent, false);

                return new ItemViewHolder(view);
            }
        };

        mPeopleRV.setAdapter(mPeopleRVAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPeopleRVAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPeopleRVAdapter.stopListening();


    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        public View mView;
        public ItemViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }
        public void setTitle(String title){
            TextView post_title = (TextView)mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }
        public void setImage(Context ctx, String image){
            ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.get().load(image).into(post_image);

        }

    }
}
