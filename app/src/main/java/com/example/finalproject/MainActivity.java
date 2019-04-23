package com.example.finalproject;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    /** List of questions user is tracking. */
    final protected ArrayList<String> questionList = new ArrayList<>();

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
                    case R.id.navigation_results:
                        Intent showResults = new Intent(MainActivity.this, Results.class);
                        startActivity(showResults);
                        return true;
                    case R.id.navigation_about:
                        Intent showAbout = new Intent(MainActivity.this, About.class);
                        startActivity(showAbout);
                        return true;
                    default:
                        return false;
                }
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

        //set up add/remove question button and menu
        final FloatingActionButton add_question = findViewById(R.id.add_remove_question);
        final PopupMenu addRemoveMenu = new PopupMenu(MainActivity.this, add_question);

        add_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRemoveMenu.getMenuInflater().inflate(R.menu.add_remove_menu, addRemoveMenu.getMenu());
                addRemoveMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.add_item) {
                            addQuestion();
                            return true;
                        } else if (item.getItemId() == R.id.remove_item) {
                            removeQuestion();
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                addRemoveMenu.show();
            }
        });
    }

    /** Add a question. */
    protected void addQuestion() {
        //prompt question and answers
    }

    /** Remove a question. */
    protected void removeQuestion() {
        //get the question to remove, remove it
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
