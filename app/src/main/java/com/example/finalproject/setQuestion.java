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

import java.util.ArrayList;
import java.util.List;


public class setQuestion extends AppCompatActivity {



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SharedPreferences.Editor editor = getSharedPreferences("PRES", MODE_PRIVATE).edit();



        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_set_question);

        TextInputLayout questionEntry = findViewById(R.id.set_answer_entry);
        final Button setQuestion1 = findViewById(R.id.set_question_button);
        setQuestion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String question = questionEntry.getEditText().getText().toString();
                if (question != null) {
                    Intent a = new Intent(setQuestion.this, MainActivity.class);
                    a.putExtra("key", question);
                    editor.putString("question", question);
                    editor.putString("text", "changed");
                    editor.apply();
                    startActivity(a);
                }
            }
        });


    }


}
