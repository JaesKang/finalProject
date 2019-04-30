package com.example.finalproject;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /** List of question data for all questions. */
    private QuestionData questionData = new QuestionData("Example question", new ArrayList<>());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set up navigation bar
        final BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(v -> {
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
        });

        //set up answer spinner
        final Spinner answerSpinner = findViewById(R.id.answerSpinner);
        setAnswerSpinner(answerSpinner);

        //set up question-answering button
        final Button submitAnswer = findViewById(R.id.submitAnswer);
        submitAnswer.setOnClickListener(v -> {
            Object selection = answerSpinner.getSelectedItem();
            if (selection != null) {
                questionData.addAnswer(selection.toString());
            }
        });

        //set up question-changing button
        final Button changeQuestion = findViewById(R.id.changeQuestion);
        changeQuestion.setOnClickListener(v -> {
            setContentView(R.layout.layout_set_question);
            //set up question-entry
            TextInputLayout questionEntry = findViewById(R.id.set_answer_entry);
            Button setQuestion = findViewById(R.id.set_question_button);
            setQuestion.setOnClickListener(w -> {
                EditText question = questionEntry.getEditText();
                if (question != null) {
                    questionData = new QuestionData(question.toString());
                    setContentView(R.layout.layout_set_answers);
                }
            });
            //set up answer-entry
            TextInputLayout answerEntry = findViewById(R.id.add_answer_entry);
            Button addAnswer = findViewById(R.id.add_answer_button);
            List<String> answers = new ArrayList<>();
            addAnswer.setOnClickListener(w -> {
                EditText answer = answerEntry.getEditText();
                if (answer != null) {
                    answers.add(answer.toString());
                }
            });
            //go back to answering questions
            Button setAnswers = findViewById(R.id.set_answers_button);
            setAnswers.setOnClickListener(w -> {
                questionData.setAnswers(answers);
                setContentView(R.layout.activity_main);
            });
            setAnswerSpinner(answerSpinner);
        });
    }

    /** Populate the answer spinner with the user's chosen answers.
     *
     * @param answerSpinner the spinner to populate
     */
    private void setAnswerSpinner(final Spinner answerSpinner) {
        ArrayAdapter<String> answerAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, questionData.getPossibleAnswers());
        answerSpinner.setAdapter(answerAdapter);
    }
}
