package com.example.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.AdapterView.OnItemLongClickListener;

import android.content.DialogInterface.OnClickListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
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
    ArrayList<String> Example;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String[] categories = {"Daily", "Study", "Mood"};
        Example = new ArrayList<>(Arrays.asList(categories));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Example);
        final ListView theListView = (ListView) findViewById(R.id.categories);

        theListView.setAdapter(adapter);


        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                String value = (String) theListView.getItemAtPosition(position);

                switch (value) {
                    case "Mood":
                        Intent moodScreen = new Intent(MainActivity.this, Mood.class);
                        MainActivity.this.startActivity(moodScreen);
                        break;


                }

            }
        });

        theListView.setOnItemLongClickListener(new OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                removeItemFromList(position);

                return true;
            }
        });


        textInfo = (TextView) findViewById(R.id.info);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

    }


    private void openDialog(){
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View subView = inflater.inflate(R.layout.add_dialog, null, false);
        final EditText subEditText = (EditText)subView.findViewById(R.id.dialogEditText);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adding a new category");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newItem = subEditText.getText().toString().trim();
                if (newItem.equals("")) {
                    Toast.makeText(MainActivity.this, "Please input some texts!", Toast.LENGTH_SHORT).show();
                } else {
                    Example.add(newItem);

                    Toast.makeText(MainActivity.this, newItem + " added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();

    }
    // method to remove list item
    protected void removeItemFromList(int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                MainActivity.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Example.remove(deletePosition);
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();
            }
        });
        alert.setNegativeButton("CANCEL", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();

    }
}
