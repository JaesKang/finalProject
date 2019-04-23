package com.example.finalproject;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class questionPage extends AppCompatActivity {

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



    TextView question;
    ArrayList<String> answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_main_layout);


        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");

        TextView text = (TextView) findViewById(R.id.question);
        text.setText(message);





        String[] answers = {"Great", "Alright", "Not good"};
        answer = new ArrayList<>(Arrays.asList(answers));
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, answer);

        final ListView theListView = (ListView) findViewById(R.id.answer);

        theListView.setAdapter(adapter);
        FloatingActionButton fab = findViewById(R.id.addAnswer);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


    }
    private void openDialog(){
        LayoutInflater inflater = LayoutInflater.from(questionPage.this);
        View subView = inflater.inflate(R.layout.add_dialog, null);
        final EditText subEditText = (EditText)subView.findViewById(R.id.dialogEditText);
        final ImageView subImageView = (ImageView)subView.findViewById(R.id.image);
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        //subImageView.setImageDrawable(drawable);

        Builder builder = new Builder(this);
        builder.setTitle("Adding a new answer");
        //builder.setMessage("AlertDialog Message");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newItem2 = subEditText.getText().toString().trim();
                if (newItem2.equals("")) {
                    Toast.makeText(questionPage.this, "Please input some texts!", Toast.LENGTH_SHORT).show();
                } else {
                    answer.add(newItem2);

                    Toast.makeText(questionPage.this, newItem2 + " added", Toast.LENGTH_SHORT).show();
                }
            }
        });


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(questionPage.this, "Cancel", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }


}
