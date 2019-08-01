package com.example.foodo;

import androidx.fragment.app.Fragment;

public class CurrentFragmentsSingleton {

    private static CurrentFragmentsSingleton state = null;

    public Fragment searchState;
    public String searchTerm;

    protected CurrentFragmentsSingleton(){}

    public static synchronized CurrentFragmentsSingleton getInstance() {
        if (null == state){
            state = new CurrentFragmentsSingleton();
        }
        return state;
    }




}
