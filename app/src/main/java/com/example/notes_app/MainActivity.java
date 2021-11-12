package com.example.notes_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    static  ListView listView;
    static int count;
    private boolean isFirstLaunch = true;

    private void updateList(){
        SharedPreferences sharedPreferencesCount = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        String strCount = sharedPreferencesCount.getString("count",null);
        if(strCount==null){
            count = 0;
        }else{
            count = Integer.parseInt(strCount);
        }
        listView = findViewById(R.id.listView);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        notes = new ArrayList();
        for(int i=1;i<=count;i++) {
            Gson gson = new Gson();
            String json = sharedPreferences.getString("notes" + i, null);
            Note obj = gson.fromJson(json, Note.class);
            if (obj == null) {
                continue;
            }
            notes.add(obj.getHeader());
        }
        if(isFirstLaunch) {
            isFirstLaunch=false;
            return;
        }
        finish();
        startActivity(getIntent());
    }
    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_notes_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.add_note) {
            Intent intent = new Intent(getApplicationContext(), notes_editor.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferencesCount = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        String strCount = sharedPreferencesCount.getString("count",null);
        if(strCount==null){
            count = 0;
        }else{
            count = Integer.parseInt(strCount);
        }
        listView = findViewById(R.id.listView);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        notes = new ArrayList();
        for(int i=1;i<=count;i++) {
            Gson gson = new Gson();
            String json = sharedPreferences.getString("notes"+i, null);
            Note obj = gson.fromJson(json, Note.class);
        if (obj == null) {
           continue;
        }
        notes.add(obj.getHeader());

        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, notes);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // Going from MainActivity to NotesEditorActivity
                Intent intent = new Intent(getApplicationContext(), notes_editor.class);
                intent.putExtra("noteId", i+1);
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int noteToDelete, long l) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), notes_deletion.class);
                                intent.putExtra("noteId", noteToDelete + 1);
                                startActivity(intent);
                            }
                        }).setNegativeButton("No", null).show();
                return true;
            }
        });
    }
}