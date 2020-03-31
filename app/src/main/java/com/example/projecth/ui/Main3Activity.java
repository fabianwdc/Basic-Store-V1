package com.example.projecth.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projecth.OrderMod;
import com.example.projecth.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {

    DatabaseReference mDatabase;
    DatabaseReference mdbr2;
    FirebaseUser fu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("order");
        final FirebaseAuth fa = FirebaseAuth.getInstance();

        fu = fa.getCurrentUser();

        mdbr2 = FirebaseDatabase.getInstance().getReference().child("userdata");
        Bundle b = getIntent().getExtras();
        String id = b.getString("id");
        final String title = b.getString("prodtitle");
        String imgurl = b.getString("imgurl");
        String descrip = b.getString("desc");
        final String iid = b.getString("iid");

        id = "₹"+id;

        TextView tv1 = findViewById(R.id.textViewTi);
        TextView tv2 = findViewById(R.id.textViewDesc);
        TextView tv3 = findViewById(R.id.textViewPri);
        ImageView post_image = findViewById(R.id.imageView2);

        Button bt = findViewById(R.id.buttonbuy);
        final String finalId = id;
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Date var = Calendar.getInstance().getTime();

                String time =String.valueOf(var.getDate())+String.valueOf(var.getMonth())+String.valueOf(var.getYear())+String.valueOf(var.getHours())+String.valueOf(var.getMinutes());
                String puske = mDatabase.push().getKey();
                puske = time + puske;


                final String finalPuske = puske;
                mdbr2.child(fu.getUid()).child("Adddress").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot== null){
                            Toast.makeText(Main3Activity.this, "Add An Address And Place An Order!", Toast.LENGTH_SHORT).show();
                        } else {

                            final OrderMod om = new OrderMod(finalPuske,title,fu.getUid(),var, finalId,iid,String.valueOf(dataSnapshot.getValue()));

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(Main3Activity.this);
                            builder1.setMessage("Place Order ?");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            mDatabase.child(fu.getUid()).child(finalPuske).setValue(om);
                                            mDatabase.child("allord").child(finalPuske).setValue(om);
                                            dialog.cancel();
                                            finish();


                                        }
                                    });

                            builder1.setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });

        Picasso.get().load(imgurl).into(post_image);

        tv1.setText(title);
        tv2.setText(descrip);
        tv3.setText(id);




    }
}
