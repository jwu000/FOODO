package com.example.foodo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class RecipePage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipePage.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipePage newInstance(String param1, String param2) {
        RecipePage fragment = new RecipePage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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


        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass selected recipe info to currentFragmentsSingleton to keep track info
                CurrentFragmentsSingleton.getInstance().recipeName = recipeInfo.getString("recipe_name");
                CurrentFragmentsSingleton.getInstance().cookTime = recipeInfo.getInt("cookTime");
                CurrentFragmentsSingleton.getInstance().numServings = recipeInfo.getInt("numServings");
                CurrentFragmentsSingleton.getInstance().totalPrice = recipeInfo.getString("totalPrice");
                CurrentFragmentsSingleton.getInstance().ingredients = recipeInfo.getString("ingredients");
                CurrentFragmentsSingleton.getInstance().instructions = recipeInfo.getString("instructions");

                Fragment nextFragment = new RestaurantResults();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFragment)
                        .addToBackStack(null) //allow us to go back kind of maybe
                        .commit();
                CurrentFragmentsSingleton.getInstance().searchState = nextFragment;
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
