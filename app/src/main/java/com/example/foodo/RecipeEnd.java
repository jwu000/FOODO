package com.example.foodo;

import android.content.Context;
import android.database.CrossProcessCursor;
import android.net.Uri;
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
 * A simple {@link Fragment} subclass.
 */
public class RecipeEnd extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeEnd.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeEnd newInstance(String param1, String param2) {
        RecipeEnd fragment = new RecipeEnd();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        dbUserReference = dbChildReference.child(CurrentFragmentsSingleton.getInstance().user);
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

        recipeName.setText(CurrentFragmentsSingleton.getInstance().recipeName);
        steps.setText(CurrentFragmentsSingleton.getInstance().instructions);
        ingredients.setText(CurrentFragmentsSingleton.getInstance().ingredients);
        cookTime.setText("Time: " + CurrentFragmentsSingleton.getInstance().cookTime + " minutes");
        price.setText("Price: $" + CurrentFragmentsSingleton.getInstance().totalPrice + " for " + CurrentFragmentsSingleton.getInstance().numServings + " servings");

        cook_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment nextFragment = new SearchRecipe();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFragment)
                        .addToBackStack(null) //allow us to go back kind of maybe
                        .commit();
                CurrentFragmentsSingleton.getInstance().searchState = nextFragment;


                DatabaseReference newHistory = FirebaseDatabase.getInstance().getReference().child("users")
                        .child(CurrentFragmentsSingleton.getInstance().user).child("history").child(String.valueOf(new Date().getTime()));
                Map<String,String> historyData = new HashMap<String,String>();
                historyData.put("type", "recipe");
                historyData.put("name", CurrentFragmentsSingleton.getInstance().recipeName);
                newHistory.setValue(historyData);

            }
        });

        add_favorite_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save info to favorite db
                String name = CurrentFragmentsSingleton.getInstance().recipeName;
                String ingredients = CurrentFragmentsSingleton.getInstance().ingredients;
                String totalPrice = CurrentFragmentsSingleton.getInstance().totalPrice;
                int numServings = CurrentFragmentsSingleton.getInstance().numServings;
                String instruction = CurrentFragmentsSingleton.getInstance().instructions;
                int cookTime = CurrentFragmentsSingleton.getInstance().cookTime;

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
