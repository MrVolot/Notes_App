package com.example.notes_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;

public class notes_deletion extends AppCompatActivity {
    int noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", 0);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(editor.remove("notes"+(noteId))==null){
            Toast.makeText(getApplicationContext(),"Error!",Toast.LENGTH_SHORT).show();
            finish();
        }
        editor.commit();
        reAssignArray(noteId);
        finish();
        return;
    }

    void reAssignArray(int noteId){
        int count;
        SharedPreferences sharedPreferencesCount = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        String strCount = sharedPreferencesCount.getString("count",null);
        if(strCount==null){
            count = 0;
        }else{
            count = Integer.parseInt(strCount);
        }
        if(count==1){
            return;
        }
        ArrayList<Note> notes = new ArrayList<>();
        SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);

        for(int i=1;i<=count;i++) {
            if(i==noteId){
                continue;
            }
            Gson gson = new Gson();
            String json = sharedPreferences.getString("notes"+i, null);
            Note obj = gson.fromJson(json, Note.class);
            notes.add(obj);
        }
        count--;
        SharedPreferences sharedPreferencesSaver= getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferencesSaver.edit();
        for(int i=1;i<=count;i++) {
            Gson gson = new Gson();
            String json = gson.toJson(notes.get(i-1));
            prefsEditor.putString("notes" + i, json);
            prefsEditor.commit();
        }
        SharedPreferences sharedPreferencesSaveId = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        sharedPreferencesSaveId.edit().putString("count", String.valueOf(count)).apply();
    }
}