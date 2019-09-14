package com.example.foodo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass. Fragment for a favorite recipe. Displays the favorite recipe info
 */
public class FavoriteRecipe extends Fragment {


    TextView steps;
    TextView ingredients;
    TextView cookTime;
    TextView totalPrice;
    TextView title;

    public FavoriteRecipe() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_recipe, container, false);
        final Bundle recipeInfo = getArguments();

        title = view.findViewById(R.id.favorite_recipe_title);
        steps = view.findViewById(R.id.favorite_steps);
        ingredients = view.findViewById(R.id.favorite_ingredients);
        cookTime = view.findViewById(R.id.favorite_cook_time);
        totalPrice = view.findViewById(R.id.favorite_price);

        //set recipe info from bundle from previous fragment
        title.setText(recipeInfo.getString("recipe_name"));
        steps.setText(recipeInfo.getString("instructions"));
        ingredients.setText(recipeInfo.getString("ingredients"));
        cookTime.setText("Time: " + recipeInfo.getInt("cookTime") + " minutes");
        totalPrice.setText("Price: $" + recipeInfo.getString("totalPrice") + " for " + recipeInfo.getInt("numServings") + " servings");
        return view;
    }


}
