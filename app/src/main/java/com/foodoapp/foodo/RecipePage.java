package com.foodoapp.foodo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * fragment to display recipe info
 */

public class RecipePage extends Fragment {


    Button yesButton;
    Button noButton;
    TextView steps;
    TextView ingredients;
    TextView cookTime;
    TextView totalPrice;
    TextView title;

    public RecipePage() {
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
        View v = inflater.inflate(R.layout.fragment_recipe_page, container, false);

        final Bundle recipeInfo = getArguments();

        title = v.findViewById(R.id.recipe_title);
        yesButton = v.findViewById(R.id.recipe_yes);
        noButton = v.findViewById(R.id.recipe_no);
        steps = v.findViewById(R.id.steps);
        ingredients = v.findViewById(R.id.ingredients);
        cookTime = v.findViewById(R.id.cook_time);
        totalPrice = v.findViewById(R.id.price);


        title.setText(recipeInfo.getString("recipe_name"));
        steps.setText(recipeInfo.getString("instructions"));
        ingredients.setText(recipeInfo.getString("ingredients"));
        cookTime.setText("Time: " + recipeInfo.getInt("cookTime") + " minutes");
        totalPrice.setText("Price: $" + recipeInfo.getString("totalPrice") + " for " + recipeInfo.getInt("numServings") + " servings");


        // if want to use this recipe to compare, move on to search fo restaurants
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass selected recipe info to currentFragmentsSingleton to keep track info
                CurrentSessionInfoSingleton.getInstance().recipeName = recipeInfo.getString("recipe_name");
                CurrentSessionInfoSingleton.getInstance().cookTime = recipeInfo.getInt("cookTime");
                CurrentSessionInfoSingleton.getInstance().numServings = recipeInfo.getInt("numServings");
                CurrentSessionInfoSingleton.getInstance().totalPrice = recipeInfo.getString("totalPrice");
                CurrentSessionInfoSingleton.getInstance().ingredients = recipeInfo.getString("ingredients");
                CurrentSessionInfoSingleton.getInstance().instructions = recipeInfo.getString("instructions");

                Fragment nextFragment = new RestaurantResults();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFragment)
                        .addToBackStack(null) //allow us to go back kind of maybe
                        .commit();
                CurrentSessionInfoSingleton.getInstance().searchState = nextFragment;
            }
        });

        // if dont want to see, go back to recipe results
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        return v;
    }

}
