package com.example.myapplication;

public class UserModel {

    //userDataModel for the user registration, login, and other functionalities

    private int id;
    private String name;
    private String password;
    private int petID;
    private String isActive; //used to determine the current user
    private String petName;
    private int food;
    private int medicine;


    public UserModel(int id, String name, String password, String isActive, int petID, String petName, int food, int medicine
    ) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.isActive = isActive;
        this.petID = petID;
        this.petName = petName;
        this.food = food;
        this.medicine = medicine;

    }

    public UserModel() {}



    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", petID=" + petID +
                '}';
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getMedicine() {
        return medicine;
    }

    public void setMedicine(int medicine) {
        this.medicine = medicine;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPetID() {
        return petID;
    }

    public void setPetID(int petID) {
        this.petID = petID;
    }
    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }
}
