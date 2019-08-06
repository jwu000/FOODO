package com.example.foodo;

import androidx.fragment.app.Fragment;

import org.json.JSONObject;

public class CurrentFragmentsSingleton {

    private static CurrentFragmentsSingleton state = null;

    public Fragment searchState;
    public String searchTerm;
    // store info of selected recipe
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

    protected CurrentFragmentsSingleton(){}

    public static synchronized CurrentFragmentsSingleton getInstance() {
        if (null == state){
            state = new CurrentFragmentsSingleton();
        }
        return state;
    }




}
