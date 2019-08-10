package com.example.foodo;

import androidx.fragment.app.Fragment;

import org.json.JSONObject;

public class CurrentFragmentsSingleton {

    private static CurrentFragmentsSingleton state = null;

    public String user;
    public Fragment searchState;
    public String searchTerm;
    // store info of selected recipe
    public String recipeName;
    public int cookTime;
    public String totalPrice;
    public int numServings;
    public String instructions;
    public String ingredients;

    // Store info of selected restaurant
    public String restaurantName;
    public String restaurantPrice;
    public double rating;
    public String address;
    public String distance;

    public double latitude;
    public double longtitude;

    public double myLatitude = 0.0;
    public double myLongitude = 0.0;

    protected CurrentFragmentsSingleton(){}

    public static synchronized CurrentFragmentsSingleton getInstance() {
        if (null == state){
            state = new CurrentFragmentsSingleton();
        }
        return state;
    }




}
