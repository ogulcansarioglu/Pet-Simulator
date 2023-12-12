package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    //database helper for the UserModel, Registration and Login activities

    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_USER_PASSWORD = "USER_PASSWORD";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_PET_ID = "PET_ID";
    public static final String COLUMN_PET_NAME = "PET_NAME";
    public static final String COLUMN_IS_ACTIVE = "IS_ACTIVE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT," + COLUMN_IS_ACTIVE + " TEXT, " + COLUMN_PET_NAME + " TEXT, "+ COLUMN_PET_ID + " INT)";
        db.execSQL(createTableStatement);

    }
    //invoked if the database version changes.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("create Table users(username TEXT primary key, password TEXT, pet Text)");

    }
    //insert functions to insert the user to the database,
    public boolean insertUser(String username, String password, String isActive) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_NAME, username);
        cv.put(COLUMN_USER_PASSWORD, password);
        cv.put(COLUMN_IS_ACTIVE, "True");
        cv.put(COLUMN_PET_NAME, "");
        long result = db.insert(USER_TABLE, null, cv);
        if(result == -1) return false;
        else
            return true;
    }
    //function to update the pet assiged to the particilar user
    public void updatePet(String pet, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        cv.put("pet", pet);
        db.update("users", cv, "username=?", new String[]{username});
    }
    //checks the user name with rawquery
    public boolean checkUserName(String username) {

        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from " + USER_TABLE +" where " + COLUMN_USER_NAME +" = ?", new String[]{username});
        if (cursor.getCount() > 0) {
            return  true;
        } else {
            return false;
        }
    }

    //checks both user name and password with the similar logic to checkUserName
    public boolean checkUserNamePassword(String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from " + USER_TABLE +" where " + COLUMN_USER_NAME +" = ? and "+ COLUMN_USER_PASSWORD + " = ?", new String[]{username, password});
        if(cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    //returns a UserModel made out of the user information, the query is done by the name which is unique in the design
    public UserModel getUser(String username) {

        String queryString = "Select * FROM " + USER_TABLE + " where " + COLUMN_USER_NAME +" = " +username;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from " + USER_TABLE +" where " + COLUMN_USER_NAME +" = ?", new String[]{username});

        if (cursor.moveToFirst()) {

                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String isActive = cursor.getString(3);
                String petName = cursor.getString(4);
                int petID = cursor.getInt(5);
                return new UserModel(userID, userName, password, isActive, petID
                , petName, 10, 10);

        }

        //return an "error" model if the cursor is empty
        return new UserModel(-1, "error", "error", "error", -1, "error", -1, -1);


    }

}
