package com.example.finalproject;

import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /** List of question data for all questions. */
    private QuestionData questionData = new QuestionData("Example question", new ArrayList<>());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //set up navigation bar
        final BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(v -> {
            switch (v.getItemId()) {
                case R.id.navigation_answer:
                    setContentView(R.layout.activity_main);
                    return true;
                case R.id.navigation_results:
                    setContentView(R.layout.layout_results);
                    return true;
                case R.id.navigation_about:
                    setContentView(R.layout.layout_about);
                    return true;
                default:
                    return false;
            }
        });


        //get display up-to-date
        updateDisplay();

        //set up question-answering
        final Spinner answerSpinner = findViewById(R.id.answerSpinner);
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
            //set up answer entry
            List<String> answers = new ArrayList<>();
            TextInputLayout answerEntry = findViewById(R.id.add_answer_entry);
            Button addAnswer = findViewById(R.id.add_answer_button);
            addAnswer.setOnClickListener(w -> {
                EditText answer = answerEntry.getEditText();
                if (answer != null) {
                    answers.add(answer.toString());
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
            });
            //go back to answering questions
            Button setAnswers = findViewById(R.id.set_answers_button);
            setAnswers.setOnClickListener(w -> {
                questionData.setAnswers(answers);
                setContentView(R.layout.activity_main);
            });
            updateDisplay();
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

        //answer table
        TableRow[] frequencyRows = {
                findViewById(R.id.row0), findViewById(R.id.row1), findViewById(R.id.row2),
                findViewById(R.id.row3), findViewById(R.id.row4)
        };
        List<QuestionData.Frequency> frequencies = questionData.getFrequencies();

        for (int i = 0; i < frequencies.size() && i < frequencyRows.length; i++) {
            frequencyRows[i].setVisibility(View.VISIBLE);
            TextView question = (TextView) frequencyRows[i].getChildAt(0);
            question.setText(frequencies.get(i).getAnswer());
            TextView frequency = (TextView) frequencyRows[i].getChildAt(1);
            frequency.setText(frequencies.get(i).getFrequency());
        }
        for (int i = frequencies.size(); i < frequencyRows.length; i++) {
            if (frequencyRows[i] == null) {
                break;
            }
            frequencyRows[i].setVisibility(View.GONE);
        }

        //Chi-squared test
        TextView pValue = findViewById(R.id.p_value);
        pValue.setText(questionData.chiSquaredTest());
    }

    /** Getter for questionData. */
    QuestionData getQuestionData() {
        return questionData;
    }
}
