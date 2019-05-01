package com.example.finalproject;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;


import java.util.ArrayList;
import java.util.List;

public class result extends AppCompatActivity {



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_results);


        //set up navigation bar
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_answer:
                        Intent a = new Intent(result.this, MainActivity.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_results:
                        setContentView(R.layout.layout_results);
                        break;

                    case R.id.navigation_about:
                        setContentView(R.layout.layout_about);
                        break;
                }
                return false;
            }

        });

        SharedPreferences prefs = getSharedPreferences("PRES", MODE_PRIVATE);

        //answer table
        TableRow[] frequencyRows = {
                findViewById(R.id.row0), findViewById(R.id.row1), findViewById(R.id.row2),
                findViewById(R.id.row3), findViewById(R.id.row4)
        };
        TextView answer0 = findViewById(R.id.answer0);
        TextView answer1 = findViewById(R.id.answer1);
        TextView answer2 = findViewById(R.id.answer2);
        TextView answer3 = findViewById(R.id.answer3);
        TextView answer4 = findViewById(R.id.answer4);

        TextView freq0 = findViewById(R.id.frequency0);
        TextView freq1 = findViewById(R.id.frequency1);
        TextView freq2 = findViewById(R.id.frequency2);
        TextView freq3 = findViewById(R.id.frequency3);
        TextView freq4 = findViewById(R.id.frequency4);

        TextView pValue = findViewById(R.id.p_value);

        String restoredText = prefs.getString("text", null);


        String name0 = "??";
        String name1 = "??";
        String name2 = "??";
        String name3 = "??";
        String name4 = "??";

        int frequency0 = 0;
        int frequency1 = 0;
        int frequency2 = 0;
        int frequency3 = 0;
        int frequency4 = 0;
        int size = 1;


        if (restoredText != null) {
            name0 = prefs.getString("name0", "??");
            name1 = prefs.getString("name1", "??");
            name2 = prefs.getString("name2", "??");
            name3 = prefs.getString("name3", "??");
            name4 = prefs.getString("name4", "??");
            frequency0 = prefs.getInt("answer0", 0);
            frequency1 = prefs.getInt("answer1", 0);
            frequency2 = prefs.getInt("answer2", 0);
            frequency3 = prefs.getInt("answer3", 0);
            frequency4 = prefs.getInt("answer4", 0);
            size = prefs.getInt("answerSize", 1);

        }
        answer0.setText(name0);
        answer1.setText(name1);
        answer2.setText(name2);
        answer3.setText(name3);
        answer4.setText(name4);

        freq0.setText(Integer.toString(frequency0));
        freq1.setText(Integer.toString(frequency1));
        freq2.setText(Integer.toString(frequency2));
        freq3.setText(Integer.toString(frequency3));
        freq4.setText(Integer.toString(frequency4));

        //Chi-squared test
        int totalCount = frequency0 + frequency1 + frequency2 + frequency3 + frequency4;

        int expectedFrequency = totalCount / size;

        ArrayList<Integer> countArray = new ArrayList<>();

        double chiStat = 0;

        if (size == 1) {
            countArray.add(frequency0);
        }
        if (size == 2) {
            countArray.add(frequency0);
            countArray.add(frequency1);
        }
        if (size == 3) {
            countArray.add(frequency0);
            countArray.add(frequency1);
            countArray.add(frequency2);
        }
        if (size == 4) {
            countArray.add(frequency0);
            countArray.add(frequency1);
            countArray.add(frequency2);
            countArray.add(frequency3);
        }
        if (size == 5) {
            countArray.add(frequency0);
            countArray.add(frequency1);
            countArray.add(frequency2);
            countArray.add(frequency3);
            countArray.add(frequency4);
        }
        for (int i = 0; i < size; i++) {

            chiStat += Math.pow(countArray.get(i) - expectedFrequency, 2) / expectedFrequency;
        }

        if (size <= 1) {
            pValue.setText("Not enough answers for Chi-squared test");
        } else {
            ChiSquaredDistribution distribution = new ChiSquaredDistribution(size - 1);
            String pvalue = Double.toString(1 - distribution.cumulativeProbability(chiStat));
            pValue.setText(pvalue);
        }









    }
}
