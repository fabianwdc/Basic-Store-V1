package com.example.projecth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Date;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView mPeopleRV;
    private DatabaseReference mDatabase;
    FirebaseUser fu;
    private FirebaseRecyclerAdapter<OrderMod, ItemViewHolder> mPeopleRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.tbtest);
        setSupportActionBar(myToolbar);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("order");
        mDatabase.keepSynced(true);

        final FirebaseAuth fa = FirebaseAuth.getInstance();

        fu = fa.getCurrentUser();

        mPeopleRV = (RecyclerView) findViewById(R.id.myRecycleView5);

        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child("order").child("allord");
        Query personsQuery = personsRef.orderByKey();

        mPeopleRV.hasFixedSize();
        mPeopleRV.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<OrderMod>().setQuery(personsQuery, OrderMod.class).build();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        mPeopleRV.setLayoutManager(gridLayoutManager);
        mPeopleRVAdapter = new FirebaseRecyclerAdapter<OrderMod, ItemViewHolder>(personsOptions) {
            @Override
            protected void onBindViewHolder(ItemViewHolder holder, final int position, final OrderMod model) {

                holder.setTitle(model.getDv().toString());
                holder.setLead(model.getProdtit());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String nameof = model.getKeystr();
                        final String prodid = model.getPid();
                        final Date dv=model.getDv();

                        final String prc =  model.getPrc();
                        final String addre = model.getSadd();
                        final String title = model.getProdtit();

                        String dates = dv.toString();
                        Intent intent = new Intent(getApplicationContext(), OrderDActivity.class);
                        intent.putExtra("oid", nameof);
                        intent.putExtra("prodtitle",title);
                        intent.putExtra("date",dates);
                        intent.putExtra("price",prc);
                        intent.putExtra("address",addre);

                        startActivity(intent);
                    }
                });
            }

            @Override
            public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_row2, parent, false);

                return new ItemViewHolder(view);
            }
        };

        mPeopleRV.setAdapter(mPeopleRVAdapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
         if(id == R.id.singout5){
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(AdminActivity.this, "Logged out", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(AdminActivity.this,MainActivity.class));
                            finish();
                        }
                    });
        }

        return super.onOptionsItemSelected(item);
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
            TextView post_title = (TextView)mView.findViewById(R.id.post_title2);
            post_title.setText(title);

        }


        public void setLead(String prodtit) {
            TextView post_title = (TextView)mView.findViewById(R.id.post_lead);
            post_title.setText(prodtit);
        }
    }
}
