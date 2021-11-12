package com.example.notes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashSet;

public class notes_editor extends AppCompatActivity {
    private int noteId;
    private boolean savedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_editor);
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", 0);

        EditText editTextHeading = findViewById(R.id.editTextTextPersonName);
        EditText editText = findViewById(R.id.editText);
        Button saveButton = findViewById(R.id.btn1);
        if(noteId!=0){
            savedItem=true;
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("notes"+noteId, null);
            Note obj = gson.fromJson(json, Note.class);
            editTextHeading.setText(obj.getHeader());
            editText.setText(obj.getText());
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(savedItem){
                    Note tmpNote = new Note();
                    if(editTextHeading.getText().toString().trim().length() == 0){
                        Toast.makeText(getApplicationContext(),"Enter some header!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(editText.getText().toString().trim().length() == 0){
                        Toast.makeText(getApplicationContext(),"Enter some text!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    tmpNote.setHeader(editTextHeading.getText().toString());
                    tmpNote.setText(editText.getText().toString());

                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(tmpNote);
                    prefsEditor.putString("notes"+noteId,json);
                    prefsEditor.commit();
                    finish();
                    return;
                }
                Note tmpNote = new Note();
                if(editTextHeading.getText().toString().trim().length() == 0){
                    Toast.makeText(getApplicationContext(),"Enter some header!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(editText.getText().toString().trim().length() == 0){
                    Toast.makeText(getApplicationContext(),"Enter some text!",Toast.LENGTH_SHORT).show();
                    return;
                }
                tmpNote.setHeader(editTextHeading.getText().toString());
                tmpNote.setText(editText.getText().toString());
                SharedPreferences sharedPreferencesCount = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                String strCount = sharedPreferencesCount.getString("count",null);
                if(strCount==null){
                    noteId = 1;
                }else{
                    noteId = Integer.parseInt(strCount)+1;
                }
                SharedPreferences sharedPreferencesSaveId = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                sharedPreferencesSaveId.edit().putString("count", String.valueOf(noteId)).apply();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(tmpNote);
                prefsEditor.putString("notes"+noteId,json);
                prefsEditor.commit();
                finish();
            }
        });
    }
}