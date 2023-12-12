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

public class AuthActivity extends AppCompatActivity {

    EditText username, password, repassword;
    Button signup, signin;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        //set up connections to the scene actors

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);

        signup = (Button) findViewById(R.id.btnsignup);
        signin = (Button) findViewById(R.id.btnsignin);

        //create a database helper for user registration

        db = new DatabaseHelper(AuthActivity.this);


        signup.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the username and password from user input

                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                //check if any of them empty

                if (user.equals("") || pass.equals("") || repass.equals("")) {
                    Toast.makeText(AuthActivity.this, "The fields are empty", Toast.LENGTH_SHORT).show();

                } else {
                    //check if the password are same or not
                    if (pass.equals(repass)) {
                        //check if the user already exits
                        Boolean checkuser = db.checkUserName(user);
                        if (checkuser == false) {
                            //insert user to the user database
                            Boolean insert = db.insertUser(user, pass, "True");
                            if (insert == true) {
                                //show Registered message and navigate to MainScreen

                                Toast.makeText(AuthActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor;
                                editor = PreferenceManager.getDefaultSharedPreferences(AuthActivity.this).edit();
                                editor.putString("USERNAME", user);
                                editor.putString("food", "10");
                                editor.putString("medicine", "10");
                                editor.apply();

                                Intent intent = new Intent(getApplicationContext(), SelectPetActivity.class);
                                startActivity(intent);


                            } else {
                                Toast.makeText(AuthActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AuthActivity.this, "User Already Exist!", Toast.LENGTH_SHORT).show();
                        }} else {
                            Toast.makeText(AuthActivity.this, "Passwords are not match", Toast.LENGTH_SHORT).show();

                        }
                    }
                }


        });
        //go to the login
        signin.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to the login if already registered
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }

        });
    }


}
