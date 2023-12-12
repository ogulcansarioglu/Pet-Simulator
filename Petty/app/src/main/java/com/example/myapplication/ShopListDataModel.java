package com.example.myapplication;

public class ShopListDataModel {

    //basic datamodel for shopping
    String name;
    String type;
    String cost;


    public ShopListDataModel(String name, String type, String cost) {
        this.name=name;
        this.type=type;
        this.cost =cost;

    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCost() {
        return cost;
    }



}