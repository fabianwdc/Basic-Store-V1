package com.example.projecth.ui.send;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecth.Item;
import com.example.projecth.ItemActivity;
import com.example.projecth.R;
import com.example.projecth.ui.Main3Activity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class SendFragment extends Fragment {



    private RecyclerView mPeopleRV;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Item, ItemActivity.ItemViewHolder> mPeopleRVAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_send, container, false);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Item");
        mDatabase.keepSynced(true);

        mPeopleRV = (RecyclerView) root.findViewById(R.id.myRecycleView2);

        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child("Item");
        Query personsQuery = personsRef.orderByKey();

        mPeopleRV.hasFixedSize();
        mPeopleRV.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<Item>().setQuery(personsQuery, Item.class).build();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        mPeopleRV.setLayoutManager(gridLayoutManager);
        mPeopleRVAdapter = new FirebaseRecyclerAdapter<Item, ItemActivity.ItemViewHolder>(personsOptions) {
            @Override
            protected void onBindViewHolder(ItemActivity.ItemViewHolder holder, final int position, final Item model) {
                holder.setTitle(model.getTitle());
                holder.setImage(getContext(), model.getImage());
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String iid3 = model.getIid();
                        final String url = model.getPrc();
                        final String nameof = model.getTitle();
                        final String imgurl = model.getImage();
                        final String descprod = model.getDesc();
//                        Intent intent = new Intent(getApplicationContext(), ItemWebView.class);
                        Intent intent = new Intent(getContext(), Main3Activity.class);
                        intent.putExtra("id", url);
                        intent.putExtra("prodtitle",nameof);
                        intent.putExtra("imgurl",imgurl);
                        intent.putExtra("desc",descprod);
                        intent.putExtra("iid",iid3);


                        startActivity(intent);
                    }
                });
            }

            @Override
            public ItemActivity.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_row, parent, false);

                return new ItemActivity.ItemViewHolder(view);
            }
        };

        mPeopleRV.setAdapter(mPeopleRVAdapter);


        return root;
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