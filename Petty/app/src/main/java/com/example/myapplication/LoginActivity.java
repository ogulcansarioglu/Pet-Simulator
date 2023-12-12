package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button btnlogin, btnsignup;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //sets up connection to scene actors and gets databasehelper
        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        btnlogin = (Button) findViewById(R.id.btnsignin);
        btnsignup = (Button) findViewById(R.id.btnsignup1);
        db = new DatabaseHelper(this);

        //if login button is pressed, invokes the OnClick method
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //gets the username and password

                String user = username.getText().toString();
                String pass = password.getText().toString();

                //check if they are empty

                if (user.equals("") || pass.equals("")) {

                    Toast.makeText(LoginActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();

                } else {
                    //checks the name and password
                    Boolean checkuserpass = db.checkUserNamePassword(user, pass);
                    if(checkuserpass == true) {
                        //display a text to the user saying the login is sucessfull
                        Toast.makeText(LoginActivity.this, "Sign in Succesfully", Toast.LENGTH_SHORT).show();
                        //sends the starting information to the main

                        SharedPreferences.Editor editor;
                        editor = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit();
                        editor.putString("USERNAME", user);
                        editor.putString("food", "10");
                        editor.putString("medicine", "10");
                        editor.putString("mood", "" + "100");
                        editor.putString("hunger",""+ "100");
                        editor.putString("health", "" + "100");
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), SelectPetActivity.class);
                        startActivity(intent);

                    } else {
                        //gives the error message

                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        //navigates user to AuthActivity to register if they are not
        btnsignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
                startActivity(intent);

            }
        });
    }

}
