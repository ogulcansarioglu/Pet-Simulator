package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SelectPetActivity extends AppCompatActivity {

    ImageButton dog, cat, bird;
    DatabaseHelper db = new DatabaseHelper(this);
    String isDead;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        dog = (ImageButton) findViewById(R.id.dog_btn);
        cat = (ImageButton) findViewById(R.id.cat_btn);


        //if the pet is dead (run away) then display that massage and prompt user to select new one

        isDead = PreferenceManager.getDefaultSharedPreferences(SelectPetActivity.this).getString("petStatus", "");
        if (isDead == "Dead") {
            Toast.makeText(SelectPetActivity.this, "Unfortunately Your pet ran away! Select a new one.", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor;
            editor = PreferenceManager.getDefaultSharedPreferences(SelectPetActivity.this).edit();
            editor.putString("petStatus", "Alive");
            editor.putString("mood", "100");
            editor.putString("health", "100");
            editor.putString("hunger", "100");
            editor.putString("food", "10");
            editor.putString("medicine", "10");
            editor.apply();
        }


        //click on dog imagebutton, sets the static variable of the kind of pet, and sends to MainActivity

        dog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor;
                editor = PreferenceManager.getDefaultSharedPreferences(SelectPetActivity.this).edit();
                editor.putString("Pet", "Dog");
                editor.putString("food", "10");
                editor.putString("medicine", "10");
                editor.putString("mood", "" + "100");
                editor.putString("hunger",""+ "100");
                editor.putString("health", "" + "100");
                editor.putString("Pet", "Dog");
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }});

        //click on cat imagebutton, sets the static variable of the kind of pet, and sends to MainActivity
        cat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor;
                editor = PreferenceManager.getDefaultSharedPreferences(SelectPetActivity.this).edit();
                editor.putString("Pet", "Cat");
                editor.putString("food", "10");
                editor.putString("medicine", "10");
                editor.putString("mood", "" + "100");
                editor.putString("hunger",""+ "100");
                editor.putString("health", "" + "100");
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }});


    }
}
