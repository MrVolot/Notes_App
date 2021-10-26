package com.example.notes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;

public class notes_editor extends AppCompatActivity {
    int noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_editor);
        EditText editTextHeading = findViewById(R.id.editTextTextPersonName);
        EditText editText = findViewById(R.id.editText);


        // Fetch data that is passed from MainActivity
        Intent intent = getIntent();

        // Accessing the data using key and value
        noteId = intent.getIntExtra("noteId", -1);
        if (noteId != -1) {
            Toast.makeText(getApplicationContext(),"Taking text!",Toast.LENGTH_SHORT).show();
            editText.setText(MainActivity.notes.get(noteId));

        } else {
            //Toast.makeText(getApplicationContext(),"Incorrect actions!",Toast.LENGTH_SHORT).show();
            MainActivity.notes.add("");
            noteId = MainActivity.notes.size() - 1;
            MainActivity.arrayAdapter.notifyDataSetChanged();

        }


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // add your code here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editTextHeading.setText();
                MainActivity.notes.set(noteId, String.valueOf(charSequence));
                //MainActivity.notes.set(noteId, String.valueOf(charSequence));
               // MainActivity.notesHeader.set(noteHeaderId, editTextHeading.getText().toString().trim());
                MainActivity.arrayAdapter.notifyDataSetChanged();
                // Creating Object of SharedPreferences to store data in the phone
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // add your code here
            }
        });
    }
}