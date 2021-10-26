package com.example.notes_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.HashSet;

public class notes_deletion extends AppCompatActivity {
    int noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);
        super.onCreate(savedInstanceState);

        MainActivity.notes.remove(noteId);
        MainActivity.arrayAdapter.notifyDataSetChanged();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        HashSet<String> set = new HashSet(MainActivity.notes);
        Intent intentNew = new Intent(getApplicationContext(), MainActivity.class);
        intentNew.putExtra("noteId", 0);
        startActivity(intentNew);
        sharedPreferences.edit().putStringSet("notes", set).apply();
    }

}