package com.example.finalproject;

import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

import android.content.SharedPreferences;


import java.util.ArrayList;
import java.util.*;


public class editAnswer extends AppCompatActivity {


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SharedPreferences.Editor editor = getSharedPreferences("PRES", MODE_PRIVATE).edit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_set_answers);

        //set up answer entry
        ArrayList<String> answers = new ArrayList<>();
        TextInputLayout answerEntry = findViewById(R.id.add_answer_entry);
        Button addAnswer = findViewById(R.id.add_answer_button);
        addAnswer.setOnClickListener(w -> {
            String answer = answerEntry.getEditText().getText().toString();
            if (answer != null) {
                answers.add(answer);
                answerEntry.getEditText().setText("");
                Toast.makeText(getApplicationContext(), answer + " added", Toast.LENGTH_LONG).show();
            }
            if (answers.size() >= QuestionData.MAX_ANSWERS) {
                addAnswer.setVisibility(View.INVISIBLE);
            }
        });
        //set up answer removal
        Button removeAnswers = findViewById(R.id.remove_answers);
        removeAnswers.setOnClickListener(w -> {
            answers.retainAll(new ArrayList<String>());
            addAnswer.setVisibility(View.VISIBLE);
            editor.remove("answers");

            editor.remove("answer0");
            editor.remove("answer1");
            editor.remove("answer2");
            editor.remove("answer3");
            editor.remove("answer4");

            editor.remove("name0");
            editor.remove("name1");
            editor.remove("name2");
            editor.remove("name3");
            editor.remove("name4");

            editor.remove("answerSize");
            editor.apply();
            Toast.makeText(getApplicationContext(), "All answers deleted", Toast.LENGTH_LONG).show();
        });


        //go back to answering questions
        Button setAnswers = findViewById(R.id.set_answers_button);
        setAnswers.setOnClickListener(w -> {
            Intent a = new Intent(editAnswer.this, MainActivity.class);
            a.putStringArrayListExtra("answers", answers);
            Set<String> set = new HashSet<String>();
            set.addAll(answers);
            editor.putStringSet("answers", set);
            editor.apply();
            startActivity(a);
        });


    }



}
