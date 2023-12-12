package com.example.myapplication;

public class InventoryDataModel {

    //this is the data model for Inventory


    private String userName;
    private int foodCount;
    private int medicineCount;



    private int id;

    public  InventoryDataModel(String name, int foodCount, int medicineCount) {
            this.userName= name;
            this.foodCount= foodCount;
            this.medicineCount = medicineCount;

        }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    public int getMedicineCount() {
        return medicineCount;
    }

    public void setMedicineCount(int medicineCount) {
        this.medicineCount = medicineCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }






}
