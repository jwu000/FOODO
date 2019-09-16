package com.foodoapp.foodo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;


import com.google.firebase.database.FirebaseDatabase;
import java.util.Date;
import java.util.HashMap;


import com.google.firebase.database.DatabaseReference;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass. Fragment for when user choses to cook in the end and use their selected recipe.
 * Displays chosen recipe information.
 */
public class RecipeEnd extends Fragment {

    DatabaseReference dbRootReference;
    private DatabaseReference dbChildReference;
    private DatabaseReference dbUserReference;
    private DatabaseReference dbFavoriteReference;
    private DatabaseReference dbRecipesReference;

    TextView recipeName;
    TextView steps;
    TextView ingredients;
    TextView cookTime;
    TextView price;

    Button cook_time;
    RadioButton add_favorite_yes;



    public RecipeEnd() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbRootReference = FirebaseDatabase.getInstance().getReference();
        dbChildReference = dbRootReference.child("users");
        dbUserReference = dbChildReference.child(CurrentSessionInfoSingleton.getInstance().user);
        dbFavoriteReference = dbUserReference.child("favorites");
        dbRecipesReference = dbFavoriteReference.child("recipes");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_end, container, false);
        add_favorite_yes = view.findViewById(R.id.add_favorite_recipe_yes);
        cook_time = view.findViewById(R.id.cook_time);
        recipeName = view.findViewById(R.id.recipe_name_end);
        steps = view.findViewById(R.id.steps);
        ingredients = view.findViewById(R.id.ingredients_end);
        cookTime = view.findViewById(R.id.recipe_cook_time_end);
        price = view.findViewById(R.id.recipe_price_end);

        recipeName.setText(CurrentSessionInfoSingleton.getInstance().recipeName);
        steps.setText(CurrentSessionInfoSingleton.getInstance().instructions);
        ingredients.setText(CurrentSessionInfoSingleton.getInstance().ingredients);
        cookTime.setText("Time: " + CurrentSessionInfoSingleton.getInstance().cookTime + " minutes");
        price.setText("Price: $" + CurrentSessionInfoSingleton.getInstance().totalPrice + " for " + CurrentSessionInfoSingleton.getInstance().numServings + " servings");

        //set listener for button indication user is done reading information and to restart to beginning of process
        cook_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment nextFragment = new SearchRecipe();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFragment)
                        .addToBackStack(null) //allow us to go back kind of maybe
                        .commit();
                CurrentSessionInfoSingleton.getInstance().searchState = nextFragment;


                DatabaseReference newHistory = FirebaseDatabase.getInstance().getReference().child("users")
                        .child(CurrentSessionInfoSingleton.getInstance().user).child("history").child(String.valueOf(new Date().getTime()));
                Map<String,String> historyData = new HashMap<String,String>();
                historyData.put("type", "recipe");
                historyData.put("name", CurrentSessionInfoSingleton.getInstance().recipeName);
                newHistory.setValue(historyData);

            }
        });

        // puts recipe as a favorite into database if radio button selected
        add_favorite_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save info to favorite db
                String name = CurrentSessionInfoSingleton.getInstance().recipeName;
                String ingredients = CurrentSessionInfoSingleton.getInstance().ingredients;
                String totalPrice = CurrentSessionInfoSingleton.getInstance().totalPrice;
                int numServings = CurrentSessionInfoSingleton.getInstance().numServings;
                String instruction = CurrentSessionInfoSingleton.getInstance().instructions;
                int cookTime = CurrentSessionInfoSingleton.getInstance().cookTime;

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("ingredients", ingredients);
                hashMap.put("price", totalPrice);
                hashMap.put("servings", numServings);
                hashMap.put("steps", instruction);
                hashMap.put("time", cookTime);

                dbRecipesReference.child(name).setValue(hashMap);

            }
        });
        return view;
    }

}
