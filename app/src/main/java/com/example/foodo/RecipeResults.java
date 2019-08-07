package com.example.foodo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import util.AdapterRecipeResults;
import util.RecipeResultAdapterItem;

import util.AdapterRecipeResults;
import util.RecipeResultAdapterItem;


public class RecipeResults extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView recipeResults;
    Spinner sort;

    ArrayList<RecipeResultAdapterItem> listOfRecipeResults = new ArrayList<>();
    RequestQueue queue;
    AdapterRecipeResults myAdapter;

    public RecipeResults() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeResults.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeResults newInstance(String param1, String param2) {
        RecipeResults fragment = new RecipeResults();
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
        View v = inflater.inflate(R.layout.fragment_recipe_results, container, false);
        recipeResults = v.findViewById(R.id.recipe_results);
        // set array of items on dropdown
        sort = (Spinner) v.findViewById(R.id.sort);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.sort_dropdown, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort.setAdapter(adapter);

        queue = Volley.newRequestQueue(getActivity());

        Bundle query = getArguments();

        String searchTerm = CurrentFragmentsSingleton.getInstance().searchTerm;

        String url = String.format("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/search?number=10&offset=0&limitLicense=false&instructionsRequired=true&query=%s", searchTerm);

        if (listOfRecipeResults.size() == 0) {
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        populateResultsList(response.getJSONArray("results"), recipeResults);
                    } catch(Exception e){
                        Log.d("JSON ERROR" , e.getMessage());
                    };
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("resquestFAIL", error.getMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("X-RapidAPI-Host",getString(R.string.api_host));
                    headers.put("X-RapidAPI-Key", getString(R.string.api_key));
                    return headers;
                }
            };
            queue.add(jsonRequest);
        }
        else{
            myAdapter = new AdapterRecipeResults(getActivity(), listOfRecipeResults);
            recipeResults.setAdapter(myAdapter);
            sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Collections.sort(listOfRecipeResults, new Comparator<RecipeResultAdapterItem>() {
                        @Override
                        public int compare(RecipeResultAdapterItem recipeResultAdapterItem, RecipeResultAdapterItem t1) {
                            return recipeResultAdapterItem.getTotalPrice() > t1.getTotalPrice() ? 1
                                    : recipeResultAdapterItem.getTotalPrice() < t1.getTotalPrice() ? -1
                                    : 0;
                        }
                    });
                    myAdapter.notifyDataSetChanged();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });


            recipeResults.setOnItemClickListener(new ListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Fragment nextFragment = new RecipePage();
                    Bundle recipeInfo = new Bundle();
                    RecipeResultAdapterItem theRecipe = listOfRecipeResults.get(i);
                    recipeInfo.putString("recipe_name", theRecipe.getRecipeName());
                    recipeInfo.putInt("cookTime", theRecipe.getTimeNeeded());
                    recipeInfo.putString("totalPrice", new DecimalFormat("#.##").format(theRecipe.getTotalPrice()));
                    recipeInfo.putInt("numServings", theRecipe.getServings());
                    recipeInfo.putString("pricePerServing", new DecimalFormat("#.##").format(theRecipe.getPricePerServing()));
                    recipeInfo.putString("instructions", parseInstructions(theRecipe.getInstructions()));
                    recipeInfo.putString("ingredients", parseIngridents(theRecipe.getIngridents()));
                    nextFragment.setArguments(recipeInfo);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, nextFragment)
                            .addToBackStack(null) //allow us to go back kind of maybe
                            .commit();
                    CurrentFragmentsSingleton.getInstance().searchState = nextFragment;
        }
    });
        }

        return v;
    }

    private String parseInstructions(JSONArray instructions) {
        StringBuilder builder = new StringBuilder();
        builder.append("Steps: \n");
        for (int index=0; index < instructions.length(); index++){
            try{
                JSONObject step = instructions.getJSONObject(index);
                builder.append(step.get("number")+". " + step.get("step") + "\n\n");
            }
            catch (Exception e){
                Log.d("error", e.toString());
            }
        }

        return builder.toString();

    }

    private String parseIngridents(JSONArray ingridents) {
        StringBuilder builder = new StringBuilder();
        builder.append("Ingredients: \n");
        for (int index=0; index < ingridents.length(); index++){
            try{
                builder.append("- " +ingridents.getJSONObject(index).get("original") + "\n");
            }
            catch (Exception e) {
                Log.d("error", e.toString());
            }

        }

        return builder.toString();
    }

    public void populateResultsList(JSONArray results, ListView listView) {
        ArrayList<Integer> recipeIds = new ArrayList<>();
        for (int index = 0; index < results.length(); index++) {
            JSONObject recipe;
            try{
                recipe = results.getJSONObject(index);
                Log.d("theRecipe", recipe.toString());
                recipeIds.add(recipe.getInt("id"));
            }
            catch (Exception e){
                Log.d("json exception" , e.toString());

            }


        }
        Log.d("recipeId", recipeIds.toString());

        StringBuilder builder = new StringBuilder();
        for (Integer recipeId: recipeIds){
            builder.append(recipeId.toString());
            builder.append("%2C");
        }
        builder.deleteCharAt(builder.length() -1);
        Log.d("the stringurl", builder.toString());
        String recipeIdsSeperatedByComma = builder.toString();
        String urlBulk = String.format("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/informationBulk?ids=%s",recipeIdsSeperatedByComma);
        Log.d("the request", urlBulk);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlBulk, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int index =0 ; index < response.length(); index++) {
                    try {
                        JSONObject recipe = (JSONObject)response.get(index);
                        int numberServings = recipe.getInt("servings");
                        String recipeName = recipe.getString("title");
                        JSONArray ingredidents = recipe.getJSONArray("extendedIngredients");
                        int numIngredidents = ingredidents.length();
                        double pricePerServing = recipe.getDouble("pricePerServing") / 100.0;
                        double totalPrice = numberServings * pricePerServing;
                        URI image = new URI(recipe.getString("image"));
                        JSONArray instructions = recipe.getJSONArray("analyzedInstructions").getJSONObject(0).getJSONArray("steps");
                        int timeNeeded = recipe.getInt("readyInMinutes");
                        RecipeResultAdapterItem recipeItem = new RecipeResultAdapterItem(recipeName,
                                totalPrice,pricePerServing,numIngredidents,timeNeeded,numberServings,image,instructions,ingredidents);
                        listOfRecipeResults.add(recipeItem);
                    }
                    catch(Exception e ) {
                        Log.d("ada",e.getMessage());
                    }


                };

                myAdapter = new AdapterRecipeResults(getActivity(), listOfRecipeResults);
                recipeResults.setAdapter(myAdapter);
                sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Collections.sort(listOfRecipeResults, new Comparator<RecipeResultAdapterItem>() {
                            @Override
                            public int compare(RecipeResultAdapterItem recipeResultAdapterItem, RecipeResultAdapterItem t1) {
                                return recipeResultAdapterItem.getTotalPrice() > t1.getTotalPrice() ? 1
                                        : recipeResultAdapterItem.getTotalPrice() < t1.getTotalPrice() ? -1
                                        : 0;
                            }
                        });
                        myAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                recipeResults.setOnItemClickListener(new ListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Fragment nextFragment = new RecipePage();
                        Bundle recipeInfo = new Bundle();
                        RecipeResultAdapterItem theRecipe = listOfRecipeResults.get(i);
                        recipeInfo.putString("recipe_name", theRecipe.getRecipeName());
                        recipeInfo.putInt("cookTime", theRecipe.getTimeNeeded());
                        recipeInfo.putString("totalPrice", new DecimalFormat("#.##").format(theRecipe.getTotalPrice()));
                        recipeInfo.putInt("numServings", theRecipe.getServings());
                        recipeInfo.putString("pricePerServing", new DecimalFormat("#.##").format(theRecipe.getPricePerServing()));
                        recipeInfo.putString("instructions", parseInstructions(theRecipe.getInstructions()));
                        recipeInfo.putString("ingredients", parseIngridents(theRecipe.getIngridents()));
                        nextFragment.setArguments(recipeInfo);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, nextFragment)
                                .addToBackStack(null) //allow us to go back kind of maybe
                                .commit();
                        CurrentFragmentsSingleton.getInstance().searchState = nextFragment;
                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("X-RapidAPI-Host",getString(R.string.api_host));
                headers.put("X-RapidAPI-Key", getString(R.string.api_key));
                return headers;
            }
        };

        jsonArrayRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 500000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 500000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                Log.d("volley timeout", error.toString());
            }
        });

        queue.add(jsonArrayRequest);

    }

}
