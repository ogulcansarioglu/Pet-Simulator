package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import java.util.ArrayList;

public class InventoryActivity extends AppCompatActivity {

    //this class showcase the inventory of the active user, populating an listView

    ListView inventoryValues;
    private InvDatabaseHelper db;
    private InventoryDataModel inventory;
    private SQLiteDatabase database;
    private ArrayList<String> inventoryCounts;




    @Override  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        //gets the necessary global information, database, and InventoryDataModel
        db = new InvDatabaseHelper(this);
        String userName = PreferenceManager.getDefaultSharedPreferences(this).getString("USERNAME", "");
        SharedPreferences.Editor editor;
        editor = PreferenceManager.getDefaultSharedPreferences(InventoryActivity.this).edit();
        inventory = db.getInventory(userName);

        //creates the bottom Navigation using MeowBottomNavigation

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottom_navigation1);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_storage_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_shopping_cart_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_logout_24));

        //navigates user by the icon they choose in the navigation

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

                if (item.getId() == 3) {
                    Intent intent = new Intent(getApplicationContext(), ShoppingActivity.class);
                    startActivity(intent);
                } else if (item.getId() == 1) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else if (item.getId() == 4) {
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
        //emphasizes the page we are in in the bottom navigation
        bottomNavigation.show(2, true);

        //populates listview using the array adapter

        Toast.makeText(InventoryActivity.this, inventory.getUserName(), Toast.LENGTH_SHORT);
        inventoryCounts = new ArrayList<String >();
        inventoryCounts.add(userName);
        inventoryCounts.add("Food : " + Integer.toString(inventory.getFoodCount()) );
        inventoryCounts.add("Medicine: " + Integer.toString(inventory.getMedicineCount()));
        inventoryValues = (ListView)findViewById(R.id.simpleListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.inventory_list, R.id.food, inventoryCounts);
        inventoryValues.setAdapter(arrayAdapter);




    }
}
