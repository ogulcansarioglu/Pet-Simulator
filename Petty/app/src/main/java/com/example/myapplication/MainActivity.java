package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;

import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //icon ids for navigation on bottomnavigation

    private final int ID_HOME = 1;
    private final int ID_MESSAGE = 2;
    private final int ID_SHOPPING = 3;
    private final int ID_LOGOUT = 4;

    //multipliers to give different speeds
    private final double playMultiplier = 0.5;
    private final double HungerMultiplier = 1.3;

    private ProgressBar progressBar;
    private int foodStatus;
    private int mentalStatus;
    private int healthStatus;
    private int foodCount, medicineCount;
    private String food, medicine;
    private TextView foodLevelView, moodLevelView, healthLevelView;
    private Handler handler = new Handler();
    private LottieAnimationView petView;
    private InvDatabaseHelper invDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper db = new DatabaseHelper(this);
        invDB = new InvDatabaseHelper(this);



        //create Interactive Bottom Navigation with Meow Library

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_MESSAGE, R.drawable.ic_baseline_storage_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_SHOPPING, R.drawable.ic_baseline_shopping_cart_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_LOGOUT, R.drawable.ic_baseline_logout_24));

        //get the right pet selected by the user in the previous screen
        petView = findViewById(R.id.petView);
        if (PreferenceManager.getDefaultSharedPreferences(this).getString("Pet", "") == "Dog")
        {
            petView.setAnimation(R.raw.dogmain);
        } else {
            petView.setAnimation(R.raw.catmain);
        }


        //get global values
        String name = PreferenceManager.getDefaultSharedPreferences(this).getString("USERNAME", "");
        String foodInv = PreferenceManager.getDefaultSharedPreferences(this).getString("food", "");
        String medInv = PreferenceManager.getDefaultSharedPreferences(this).getString("medicine", "");

        foodStatus = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this).getString("hunger", ""));
        mentalStatus = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this).getString("mood", ""));
        healthStatus = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this).getString("health", ""));



        //get the created (by helper) user model and food and medicine count
        UserModel user = db.getUser(name);
        user.setMedicine(Integer.parseInt(medInv));
        user.setFood(Integer.parseInt(foodInv));


        //insert the information into the database
        invDB.insert(new InventoryDataModel(name, 10, 10));

        //set the logic of the bottom navigation, get the ID of the selected icon and navigate to the right page
        //saves the new global variables
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

                if (item.getId() == 3) {

                    SharedPreferences.Editor editor;
                    editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
                    editor.putString("food", "" + user.getFood());
                    editor.putString("medicine", "" + user.getMedicine());
                    editor.putString("mood", "" + mentalStatus);
                    editor.putString("hunger",""+ foodStatus);
                    editor.putString("health", "" + healthStatus);
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), ShoppingActivity.class);
                    startActivity(intent);


                } else if (item.getId() == 2) {
                    //update the inventory before leaving the page
                    SharedPreferences.Editor editor;
                    editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
                    editor.putString("mood", "" + mentalStatus);
                    editor.putString("hunger",""+ foodStatus);
                    editor.putString("health", "" + healthStatus);
                    editor.apply();
                    invDB.updateInventory(new InventoryDataModel(user.getName(), user.getFood(),user.getMedicine()));
                    Intent intent = new Intent(getApplicationContext(), InventoryActivity.class);
                    startActivity(intent);
                } else if (item.getId() == 4) {
                    //update the inventory before leaving the page
                    invDB.updateInventory(new InventoryDataModel(user.getName(), user.getFood(),user.getMedicine()));
                    //since we exit, reset the statuses
                    foodStatus = 100;
                    mentalStatus = 100;
                    healthStatus = 100;

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }


            }
        });
        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

            }
        });

        //Set the progress bars and status

        bottomNavigation.show(ID_HOME, true);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ProgressBar mentalBar = (ProgressBar) findViewById(R.id.progressBar2);
        ProgressBar healthBar = (ProgressBar) findViewById(R.id.progressBar3);

        foodLevelView = (TextView) findViewById(R.id.food);
        moodLevelView = (TextView) findViewById(R.id.mood);
        healthLevelView = (TextView) findViewById(R.id.health);

        ImageButton foodButton = (ImageButton) findViewById(R.id.imageButton);
        ImageButton playButton = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton healthButton = (ImageButton) findViewById(R.id.imageButton3);
        //set click listener on foodButton

        foodButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor;
                editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
                food = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("food", "");

                foodCount = user.getFood();
                //if we have enough food, increase the foodStatus and change the animation to eating

                if (foodCount> 0) {
                    foodStatus += 10;
                    foodCount -= 1;
                    //set the eating animation depend on the pet kind
                    if (PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("Pet", "") == "Dog")
                    {
                        petView.setAnimation(R.raw.dogfood);
                    } else {
                        petView.setAnimation(R.raw.catfood);
                    }
                    //plays the animation

                    petView.playAnimation();

                    //set the foodcount as an Global Variable and its value on User Model, and inform the user

                    user.setFood(foodCount);
                    Toast.makeText(MainActivity.this, "You have " + foodCount + " Food Left!", Toast.LENGTH_SHORT).show();
                    editor.putString("food", "" + foodCount);
                    editor.putString("medicine", "" + medicineCount);
                    editor.apply();
                //create an error message for user to buy more items
                } else if (foodCount == 0){
                    Toast.makeText(MainActivity.this, "Error! Please buy Food at Market", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error! Please buy Food at Market", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Increase mental health if played button is selected and play the right animation
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //you can always play with the pet so just apply the animation and boost.

                mentalStatus += 10;
                if (PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("Pet", "") == "Dog")
                {
                    petView.setAnimation(R.raw.dogplay);

                } else {
                    petView.setAnimation(R.raw.catplay);
                }

                petView.playAnimation();

            }
        });
        healthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor;
                medicineCount = user.getMedicine();

                //give the medicine boost if there is a medicine to use

                if (medicineCount > 0){
                    healthStatus += 10;
                    medicineCount -= 1;

                    //set the medicine animation

                    if (PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("Pet", "") == "Dog")
                    {
                        petView.setAnimation(R.raw.dogangry);

                    } else {
                        petView.setAnimation(R.raw.catsleep);
                    }

                    petView.playAnimation();

                    user.setMedicine(medicineCount);
                    //inform user of how much item they have left
                    Toast.makeText(MainActivity.this, "You have " + medicineCount + " Medicine Left!", Toast.LENGTH_SHORT).show();
                    editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
                    editor.putString("medicine", "" + medicineCount);
                    editor.apply();

                    //give an error message

            } else if (medicineCount == 0){
                    Toast.makeText(MainActivity.this, "Error! Please buy Medicine at Market", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error! Please buy Medicine at Market", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Start a thread to run core logic of the app - decrease the core values and show them on the progress bar in accordance.
        //When the the core values dip below 0, the thread closes and user is navigated to select a new pet
        new Thread(new Runnable() {
            public void run() {

                //get the current statuses

                foodStatus = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("hunger", ""));
                mentalStatus = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("mood", ""));
                healthStatus = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("health", ""));

                while (foodStatus > 0 && mentalStatus > 0 && healthStatus > 0) {

                    //change the core status based on the multiplier constantly with update

                    foodStatus -= (int) 1 * HungerMultiplier;
                    mentalStatus -= (int) 1 * playMultiplier;
                    healthStatus -= (int) 1 * 0.2;

                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            mentalBar.setProgress(mentalStatus);
                            progressBar.setProgress(foodStatus);
                            healthBar.setProgress(healthStatus);
                            foodLevelView.setText(foodStatus + "/" + progressBar.getMax());
                            moodLevelView.setText(mentalStatus + "/" + progressBar.getMax());
                            healthLevelView.setText(healthStatus + "/" + progressBar.getMax());
                            //create random health events that will deduce health status using random library
                            int switchSickness = new Random().nextInt(200);
                            if (switchSickness == 1) {
                                Toast.makeText(MainActivity.this, "Got Cold! Tummy Hurts!", Toast.LENGTH_SHORT).show();
                                healthStatus -= 10;


                            }
                            else if (switchSickness == 2) {
                                Toast.makeText(MainActivity.this, "Fall from the couch! Ahh! ", Toast.LENGTH_SHORT).show();
                                healthStatus -= 10;

                            }
                            else if (switchSickness == 3) {
                                Toast.makeText(MainActivity.this, "Miss our vaccination day!", Toast.LENGTH_SHORT).show();
                                healthStatus -= 15;
                            }

                        }
                    });
                    try {
                        // Sleep for 500 milliseconds.
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //if we are below zero for any status, declare the pet "dead" and return to select pet page

                SharedPreferences.Editor editor;
                editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
                editor.putString("petStatus", "Dead");
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), SelectPetActivity.class);
                startActivity(intent);
                foodStatus = 100;
                mentalStatus = 100;
                healthStatus = 100;

            }
        }).start();
        //notify the SelectPetActivity that the animal is dead and start over by selecting a new one


    }


}