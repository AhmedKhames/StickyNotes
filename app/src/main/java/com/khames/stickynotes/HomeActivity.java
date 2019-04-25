package com.khames.stickynotes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    FloatingActionButton fab;
    private SharedPreferences loginPreferences;
    private Button logout;
    private  SharedPreferences.Editor loginEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logout = (Button)findViewById(R.id.logout);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        Intent intent = getIntent();
        loginPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToAddNewNoteActivity();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }

    private void sendUserToAddNewNoteActivity(){

        Intent addNote = new Intent(HomeActivity.this,AddNewNoteActivity.class);
        startActivity(addNote);

    }


    private void logout(){
        loginPreferences.edit().clear().commit();
        sendUserToMainActivity();
    }

    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(HomeActivity.this,MainActivity.class);
        startActivity(mainIntent);
    }
}
