package com.example.finalproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //set up navigation bar
        final BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem v) {
                switch (v.getItemId()) {
                    case R.id.navigation_answer:
                        Intent showMainActivity = new Intent(About.this, MainActivity.class);
                        startActivity(showMainActivity);
                        return true;
                    case R.id.navigation_results:
                        Intent showResults = new Intent(About.this, Results.class);
                        startActivity(showResults);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}
