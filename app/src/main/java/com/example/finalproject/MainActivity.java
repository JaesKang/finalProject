package com.example.finalproject;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    /** List of question data for all questions. */

    //SharedPreferences prefs = getSharedPreferences("PRES", MODE_PRIVATE);
    //String restoredText = prefs.getString("text", " ");

    QuestionData questionData = new QuestionData(" ", new ArrayList<>());

    int count0 = 0;
    int count1 = 0;
    int count2 = 0;
    int count3 = 0;
    int count4 = 0;

    //The key argument here must match that used in the other activity

 //   SharedPreferences prefs = getSharedPreferences("name", MODE_PRIVATE);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences.Editor editor = getSharedPreferences("PRES", MODE_PRIVATE).edit();

        SharedPreferences prefs = getSharedPreferences("PRES", MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        String exampleQuestion = " ";
        if (restoredText != null) {
            exampleQuestion = prefs.getString("question", "Enter a question");//"No name defined" is the default value.

        }

        Bundle extras = getIntent().getExtras();


        if (!(exampleQuestion.equals(" "))) {
            questionData.setQuestion(exampleQuestion);
            //The key argument here must match that used in the other activity

        } else {
            questionData.setQuestion("Set a question");
        }
        Set<String> set = prefs.getStringSet("answers", null);
        ArrayList<String> myList = new ArrayList<>();
        if (set != null) {
            myList.addAll(set);
            questionData.setAnswers(myList);
        }
        ArrayList<String> temp = questionData.getPossibleAnswers();
        for (int i = 0; i < temp.size(); i++) {
            editor.putString("name" + i, temp.get(i));
            editor.apply();
        }






        //set up navigation bar
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_answer:
                        break;
                    case R.id.navigation_results:
                        Intent a = new Intent(MainActivity.this, result.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_about:
                        Intent b = new Intent(MainActivity.this, about.class);
                        startActivity(b);
                        break;
                }
                return false;
            }

        });




        //set up question-answering
        final Spinner answerSpinner = findViewById(R.id.answerSpinner);
        final Button editAnswer = findViewById(R.id.EditAnswer);

        editAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, com.example.finalproject.editAnswer.class);
                startActivity(a);
            }
        });

        Button submitAnwer = findViewById(R.id.submit);

        count0 = prefs.getInt("answer0", 0);
        count1 = prefs.getInt("answer1", 0);
        count2 = prefs.getInt("answer2", 0);
        count3 = prefs.getInt("answer3", 0);
        count4 = prefs.getInt("answer4", 0);

        //get display up-to-date
        updateDisplay();

        submitAnwer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerSpinner.getSelectedItem() == null) {
                    Toast.makeText(getApplicationContext(), "No answer is selected", Toast.LENGTH_LONG).show();
                    return;
                }
                String selection = answerSpinner.getSelectedItem().toString();
                ArrayList<String> temp = questionData.getPossibleAnswers();

                editor.putInt("answerSize", temp.size());
                editor.apply();

                for (int i = 0; i < temp.size(); i++) {
                    if (selection.equals(temp.get(i))) {
                        if (i == 0) {
                            count0++;
                            Toast.makeText(getApplicationContext(), temp.get(i) + " submitted", Toast.LENGTH_SHORT).show();
                            editor.putInt("answer" + i, count0);
                            editor.apply();
                        }
                        if (i == 1) {
                            count1++;
                            Toast.makeText(getApplicationContext(), temp.get(i) + " submitted", Toast.LENGTH_SHORT).show();

                            editor.putInt("answer" + i, count1);
                            editor.apply();
                        }
                        if (i == 2) {
                            count2++;
                            Toast.makeText(getApplicationContext(), temp.get(i) + " submitted", Toast.LENGTH_SHORT).show();

                            editor.putInt("answer" + i, count2);
                            editor.apply();
                        }
                        if (i == 3) {
                            count3++;
                            Toast.makeText(getApplicationContext(), temp.get(i) + " submitted", Toast.LENGTH_SHORT).show();

                            editor.putInt("answer" + i, count3);
                            editor.apply();
                        }
                        if (i == 4) {
                            count4++;
                            Toast.makeText(getApplicationContext(), temp.get(i) + " submitted", Toast.LENGTH_SHORT).show();

                            editor.putInt("answer" + i, count4);
                            editor.apply();
                        }


                    }

                }


            }

        });

        //set up question-changing button
        final Button changeQuestion = findViewById(R.id.changeQuestion);

        changeQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(MainActivity.this, setQuestion.class);
                startActivity(b);
            }
        });

    }

    /** Update the question and answers in the display. */
    void updateDisplay() {
        //question
        TextView questionDisplay = findViewById(R.id.questionDisplay);
        questionDisplay.setText(questionData.getQuestion());


        //answer spinner
        Spinner answerSpinner = findViewById(R.id.answerSpinner);
        ArrayAdapter<String> answerAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, questionData.getPossibleAnswers());
        answerSpinner.setAdapter(answerAdapter);

    }

    /** Getter for questionData. */
    QuestionData getQuestionData() {
        return questionData;
    }

}
