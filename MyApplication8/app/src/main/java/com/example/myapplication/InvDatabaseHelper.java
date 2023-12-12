package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class InvDatabaseHelper extends SQLiteOpenHelper {

    public static final String INVENTORY = "INVENTORY";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_FOOD = "FOOD";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_MEDICINE = "Medicine";
    private SQLiteDatabase db;

    public InvDatabaseHelper(@Nullable Context context) {
        super(context, "inventory.db", null, 1);
    }

    //called the first time the database is accessed.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + INVENTORY + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT, "  + COLUMN_FOOD + " INT, "+ COLUMN_MEDICINE + " INT)";
        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop if the table already exits
        db.execSQL("DROP TABLE IF EXISTS " + INVENTORY);

        // Create tables again
        onCreate(db);

    }
    //insert the information from inventoryDataModel to the database
    void insert(InventoryDataModel inventory) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, inventory.getUserName());
        values.put(COLUMN_FOOD, inventory.getFoodCount());
        values.put(COLUMN_MEDICINE, inventory.getMedicineCount());


        db.insert(INVENTORY, null, values);


    }
    //creates and returns an inventory data model from the information obtained from the query
    InventoryDataModel getInventory(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cursor = db.rawQuery("Select * from " + INVENTORY +" where " + COLUMN_USER_NAME +" = ?", new String[]{userName});

        //if the cursor is not empty, get the first
        if(cursor.moveToFirst())
        {
            return new InventoryDataModel(cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
        }


        // return the user's inventory
        return new InventoryDataModel("Error", -1, -1);

    }
    //updated the inventory values when user spends food or medicine or buy them
    public int updateInventory(InventoryDataModel inventory) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOOD, inventory.getFoodCount());
        values.put(COLUMN_MEDICINE, inventory.getMedicineCount());

        // update the row
        return db.update(INVENTORY, values, COLUMN_USER_NAME + " = ?",
                new String[] { inventory.getUserName()});
    }
    // deletes a given inventory
    public void deleteTask(InventoryDataModel inventory) {

        db.delete(INVENTORY, COLUMN_ID + " = ?",
                new String[] { String.valueOf(inventory.getId()) });
        db.close();
    }

}
