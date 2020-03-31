package com.example.projecth.ui.slideshow;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projecth.R;
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

public class SlideshowFragment extends Fragment {

    DatabaseReference mDatabase;
    FirebaseAuth fa;
    FirebaseUser fu;
    private static final int RC_SIGN_IN = 123;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);


        fa = FirebaseAuth.getInstance();
        fu = fa.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String fun = fu.getDisplayName();

        if (fun!= null){
            TextView tvn = root.findViewById(R.id.textView7);
            tvn.setText(fun);
        }

        final TextView tvAdd = root.findViewById(R.id.text_addres);
        final TextView tvPhone = root.findViewById(R.id.textViewph);
        final TextView tvVehicleNum = root.findViewById(R.id.textViewvnum);

        mDatabase.child("userdata").child(fu.getUid()).child("Adddress").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue()!= null){
                    tvAdd.setText(String.valueOf(dataSnapshot.getValue()));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabase.child("userdata").child(fu.getUid()).child("Phone").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue()!= null){
                    tvPhone.setText(String.valueOf(dataSnapshot.getValue()));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.child("userdata").child(fu.getUid()).child("VNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue()!= null){
                    tvVehicleNum.setText(String.valueOf(dataSnapshot.getValue()));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View view = inflater.inflate(R.layout.layout_alert, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("Enter Address:");

                alertDialog.setCancelable(true);



                final EditText etComments = (EditText) view.findViewById(R.id.etComments);
                etComments.setHint("Enter Address:");
//                etComments.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE ,"OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        mDatabase.child("userdata").child(fu.getUid()).child("Adddress").setValue(etComments.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), "Address Updated!!!", Toast.LENGTH_SHORT).show();
                                tvAdd.setText(etComments.getText().toString());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Address Update Failed!!", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });


                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onShow(DialogInterface dialog) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.design_default_color_primary_dark);
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.colorPrimaryDark);
                    }
                });
                alertDialog.setView(view);
                alertDialog.show();
            }
        });

        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = inflater.inflate(R.layout.layout_alert, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("Enter Phone:");

                alertDialog.setCancelable(true);

                final EditText etComments = (EditText) view.findViewById(R.id.etComments);
                etComments.setHint("Phone Number:");
                etComments.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE ,"OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        mDatabase.child("userdata").child(fu.getUid()).child("Phone").setValue(etComments.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), "Phone Number Updated!!!", Toast.LENGTH_SHORT).show();
                                tvPhone.setText(etComments.getText().toString());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Phone Number Update Failed!!", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });


                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onShow(DialogInterface dialog) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.design_default_color_primary_dark);
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.colorPrimaryDark);
                    }
                });
                alertDialog.setView(view);
                alertDialog.show();
            }
        });

        tvVehicleNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = inflater.inflate(R.layout.layout_alert, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("Enter Vehicle No:");

                alertDialog.setCancelable(true);

                final EditText etComments = (EditText) view.findViewById(R.id.etComments);
                etComments.setHint("Enter Vehicle No:");
//                etComments.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE ,"OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        mDatabase.child("userdata").child(fu.getUid()).child("VNumber").setValue(etComments.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), "Vehicle Number Updated!!!", Toast.LENGTH_SHORT).show();
                                tvVehicleNum.setText(etComments.getText().toString());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Vehicle Number Update Failed!!", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });


                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onShow(DialogInterface dialog) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.design_default_color_primary_dark);
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.colorPrimaryDark);
                    }
                });
                alertDialog.setView(view);
                alertDialog.show();
            }
        });
        

        return root;
    }
}