package com.example.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Mood extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_result:
                    mTextMessage.setText(R.string.title_result);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };


    TextView textInfo;
    ArrayList<String> question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_list_layout);


        String[] question1 = {"How are you?", "second question", "third question"};
        question = new ArrayList<>(Arrays.asList(question1));
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, question);

        final ListView theListView = (ListView) findViewById(R.id.questionList);

        theListView.setAdapter(adapter);


        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                String value = (String) theListView.getItemAtPosition(position);

                switch(position) {
                    case 0:
                        Intent questionScreen = new Intent(Mood.this, questionPage.class);
                        questionScreen.putExtra("message", value);
                        Mood.this.startActivity(questionScreen);
                        break;


                }

            }
        });
        textInfo = (TextView)findViewById(R.id.info);

        FloatingActionButton fab = findViewById(R.id.addQuestions);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

    }
    private void openDialog(){
        LayoutInflater inflater = LayoutInflater.from(Mood.this);
        View subView = inflater.inflate(R.layout.add_dialog, null);
        final EditText subEditText = (EditText)subView.findViewById(R.id.dialogEditText);
        final ImageView subImageView = (ImageView)subView.findViewById(R.id.image);
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        //subImageView.setImageDrawable(drawable);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adding a new question");
        //builder.setMessage("AlertDialog Message");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newItem1 = subEditText.getText().toString().trim();
                if (newItem1.equals("")) {
                    Toast.makeText(Mood.this, "Please input some texts!", Toast.LENGTH_SHORT).show();
                } else {
                    question.add(newItem1);

                    Toast.makeText(Mood.this, newItem1 + " added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Mood.this, "Cancel", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();

    }

}