package com.example.projecth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    FirebaseAuth auth;
    FirebaseUser fu;
    ProgressDialog progressdialog;
    public final int REQUEST_CODE= 777;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressdialog = new ProgressDialog(MainActivity.this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        progressdialog = new ProgressDialog(MainActivity.this);
        progressdialog.setMessage("Please Wait....");

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS},REQUEST_CODE );
            return;

        }

        auth = FirebaseAuth.getInstance();
        progressdialog.show();
        if (auth.getCurrentUser() != null && auth != null) {
//            Intent it = new Intent(this,ItemActivity.class);
            fu = auth.getCurrentUser();

            String maic = fu.getEmail();

            if(maic.equalsIgnoreCase("adminsemail@gmail.com")){
                Intent it = new Intent(this,AdminActivity.class);
                progressdialog.dismiss();
                startActivity(it);
                finish();
            }else {

                Intent it = new Intent(this, MenuActivity.class);
                progressdialog.dismiss();
                startActivity(it);
                finish();
            }
        } else {
            progressdialog.dismiss();
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.EmailBuilder().build()
                            ))
                            .build(),
                    RC_SIGN_IN);


        }
    }
}

