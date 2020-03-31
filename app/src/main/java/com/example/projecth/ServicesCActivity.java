package com.example.projecth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projecth.ui.Main3Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

public class ServicesCActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    DatabaseReference mdbr2;
    FirebaseUser fu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main32);

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
        final EditText edt1  = findViewById(R.id.editText);

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

                        if (dataSnapshot.getValue()== null){
                            Toast.makeText(ServicesCActivity.this, "Add An Address And Place An Order!", Toast.LENGTH_SHORT).show();
                        } else {
                            final OrderMod om = new OrderMod(finalPuske,title,fu.getUid(),var, finalId,iid,String.valueOf(dataSnapshot.getValue()));


                            final String edbatnum = edt1.getText().toString();

                            if (edbatnum != null){


                                AlertDialog.Builder builder2 = new AlertDialog.Builder(ServicesCActivity.this);
                                builder2.setMessage("Place Order ?");
                                builder2.setCancelable(true);

                                builder2.setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                mDatabase.child(fu.getUid()).child(finalPuske).setValue(om);
                                                mDatabase.child("allord").child(finalPuske).setValue(om);

                                                mDatabase.child(fu.getUid()).child(finalPuske).child("bnum").setValue(edbatnum);
                                                mDatabase.child("allord").child(finalPuske).child("bnum").setValue(edbatnum);
                                                dialog.cancel();
                                                finish();
                                            }
                                        });

                                builder2.setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert12 = builder2.create();
                                alert12.show();
                            } else {
                                Toast.makeText(ServicesCActivity.this, "Enter Value!", Toast.LENGTH_SHORT).show();
                            }



                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });

//        Picasso.get().load(imgurl).into(post_image);

        tv1.setText(title);
        tv2.setText(descrip);
        tv3.setText(id);
    }
}
