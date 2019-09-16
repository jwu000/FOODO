package com.foodoapp.foodo;

import androidx.fragment.app.Fragment;

/**
    Singleton class to help store global variables to pass fragments. Keeps track of what user searched for,
    selected recipe information, selected restaurant information, user location
 */

public class CurrentSessionInfoSingleton {

    private static CurrentSessionInfoSingleton state = null;

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

    //store info of user location
    public double myLatitude = 0.0;
    public double myLongitude = 0.0;

    protected CurrentSessionInfoSingleton(){}

    public static synchronized CurrentSessionInfoSingleton getInstance() {
        if (null == state){
            state = new CurrentSessionInfoSingleton();
        }
        return state;
    }




}
