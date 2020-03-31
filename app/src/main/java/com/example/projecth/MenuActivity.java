package com.example.projecth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.view.View;
//import android.support.v4.view.GravityCompat;
//import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
//import android.support.design.widget.NavigationView;
//import android.support.v4.widget.DrawerLayout;
//
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.projecth.ui.gallery.GalleryFragment;
import com.example.projecth.ui.send.SendFragment;
import com.example.projecth.ui.slideshow.SlideshowFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main23);
        Toolbar toolbar = findViewById(R.id.toolbar23);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout24);
        NavigationView navigationView = findViewById(R.id.nav_view23);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new SendFragment())
                .commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout24);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent in = new Intent(this,COActivity.class);
            startActivity(in);

        } else if(id == R.id.singout){
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MenuActivity.this, "Logged out", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(MenuActivity.this,MainActivity.class));
                            finish();
                        }
                    });
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_prod) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new SendFragment())
                    .commit();
        } else if (id == R.id.nav_serv) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new GalleryFragment())
                    .commit();
        } else if(id == R.id.nav_prof){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new SlideshowFragment())
                    .commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout24);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
