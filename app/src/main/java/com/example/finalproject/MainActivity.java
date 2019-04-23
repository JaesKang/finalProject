package com.example.finalproject;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    /** List of questions user is tracking. */
    final private ArrayList<String> questionList = new ArrayList<>();

    //create data object to keep track of answers

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
                        MainActivity.this.setContentView(R.layout.activity_main);
                        return true;
                    case R.id.navigation_results:
                        Intent showResults = new Intent(MainActivity.this, Results.class);
                        startActivity(showResults);
                        return true;
                    case R.id.navigation_about:
                        MainActivity.this.setContentView(R.layout.content_about);
                        return true;
                }
                return false;
            }
        });

        //set up question list
        final ListView question_list = findViewById(R.id.question_list);
        ArrayAdapter<String> questionAdapter = new ArrayAdapter<>(this, R.layout.activity_main, R.id.question_list, questionList);
        question_list.setAdapter(questionAdapter);
        question_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                answerQuestion((String) question_list.getItemAtPosition(position));
            }
        });
    }

    /** Add an answer to a question.
     *
     * @param chosenQuestion the question being answered
     */
    protected void answerQuestion(final String chosenQuestion) {
        /*
        pull up the answers to this particular question
        get the user's selection
        */
    }
}
