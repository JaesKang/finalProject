package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

public class Results extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set up navigation bar
        final BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem v) {
                switch (v.getItemId()) {
                    case R.id.navigation_answer:
                        Intent showMainActivity = new Intent(Results.this, MainActivity.class);
                        startActivity(showMainActivity);
                        return true;
                    case R.id.navigation_about:
                        Intent showAbout = new Intent(Results.this, About.class);
                        startActivity(showAbout);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}