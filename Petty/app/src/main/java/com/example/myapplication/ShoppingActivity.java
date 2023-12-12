package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ShoppingActivity extends AppCompatActivity {

    //this class shows the items in the store with an option to buy

    private ArrayList<ShopListDataModel> dataModels;
    private ListView listView;
    private static CustomAdapter adapter;
    private int medicineCount, foodCount;
    private InvDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //get global values and convert them to use inside listener on the listview
        SharedPreferences.Editor editor;
        editor = PreferenceManager.getDefaultSharedPreferences(ShoppingActivity.this).edit();
        String food = PreferenceManager.getDefaultSharedPreferences(this).getString("food", "");
        String userName = PreferenceManager.getDefaultSharedPreferences(this).getString("USERNAME", "");
        final int[] foodCount = {Integer.parseInt(food)};
        String medicine = PreferenceManager.getDefaultSharedPreferences(this).getString("medicine", "");
        final int[] medicineCount = {Integer.parseInt(medicine)};

        //get inventory database and model, update it to latest values before any shopping

        db = new InvDatabaseHelper(this);
        InventoryDataModel invModel = db.getInventory(userName);
        invModel.setMedicineCount(Integer.parseInt(medicine));
        invModel.setFoodCount(Integer.parseInt(food));
        db.updateInventory(invModel);
        //get the listview
        listView=(ListView)findViewById(R.id.list);
        //create an arraylist of dataModels
        dataModels= new ArrayList<>();


        //add the medicine and foods
        dataModels.add(new ShopListDataModel("Apple Pie", "Food", "15"));
        dataModels.add(new ShopListDataModel("Tommy Medicine", "Medicine", "10"));
        dataModels.add(new ShopListDataModel("Cupcake", "Food", "3"));
        dataModels.add(new ShopListDataModel("Donut","Food","40"));
        dataModels.add(new ShopListDataModel("Pur Medicine", "Medicine", "40"));
        dataModels.add(new ShopListDataModel("Honey", "Food", "8"));
        dataModels.add(new ShopListDataModel("Gingerbread", "Food", "9"));
        dataModels.add(new ShopListDataModel("Painkiller","Food","110"));
        dataModels.add(new ShopListDataModel("Ice Cream Sandwich", "Medicine", "14"));
        dataModels.add(new ShopListDataModel("Tommy Medicine", "Food", "160"));
        dataModels.add(new ShopListDataModel("Carrot", "Medicine", "18"));
        dataModels.add(new ShopListDataModel("Longevity Medicine","Food","21"));
        dataModels.add(new ShopListDataModel("Potatoes", "Food", "230"));

        adapter= new CustomAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        //logic for shopping and adding them to inventory
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ShopListDataModel dataModel= dataModels.get(position);

                if (dataModel.type == "Medicine") {
                    medicineCount[0] += 1;

                    //apply changes to global variables for fast retrieval and ease of use

                    editor.putString("food", "" + foodCount[0]);
                    editor.putString("medicine", "" + medicineCount[0]);
                    editor.apply();

                    //apply changes to the database for long-term keeping

                    invModel.setFoodCount(foodCount[0]);
                    invModel.setMedicineCount(medicineCount[0]);
                    db.updateInventory(invModel);

                } else if (dataModel.type == "Food") {
                    foodCount[0] += 1;
                    //apply changes to global variables for fast retrieval and ease of use

                    editor.putString("food", "" + foodCount[0]);
                    editor.putString("medicine", "" + medicineCount[0]);
                    editor.apply();

                    //apply changes to the database for long-term keeping

                    invModel.setFoodCount(foodCount[0]);
                    invModel.setMedicineCount(medicineCount[0]);
                    db.updateInventory(invModel);
                }

                Snackbar.make(view, "The food count is" + foodCount[0] + "and Medicine is " + medicineCount[0], Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();



            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
