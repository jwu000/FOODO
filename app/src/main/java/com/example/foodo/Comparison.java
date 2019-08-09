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
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Comparison extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    Button cook;
    Button dine_out;
    TextView recipe_name;
    TextView recipe_price;
    TextView recipe_time;
    TextView restaurant_name;
    TextView restaurant_price;
    TextView restaurant_time;

    RequestQueue requestQueue;

    public Comparison() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Comparison.
     */
    // TODO: Rename and change types and number of parameters
    public static Comparison newInstance(String param1, String param2) {
        Comparison fragment = new Comparison();
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

        View view = inflater.inflate(R.layout.fragment_comparison, container, false);
        requestQueue = Volley.newRequestQueue(getActivity());

        recipe_name = view.findViewById(R.id.final_recipe_name);
        recipe_price = view.findViewById(R.id.final_recipe_price);
        recipe_time = view.findViewById(R.id.final_recipe_time);
        restaurant_name = view.findViewById(R.id.final_restaurant_name);
        restaurant_price = view.findViewById(R.id.final_restaurant_price);
        restaurant_time = view.findViewById(R.id.final_restaurant_time);
        cook = view.findViewById(R.id.choose_cook);
        dine_out = view.findViewById(R.id.choose_dine_out);

        recipe_name.setText(CurrentFragmentsSingleton.getInstance().recipeName);
        recipe_price.setText("Price: " + CurrentFragmentsSingleton.getInstance().totalPrice + " for " + CurrentFragmentsSingleton.getInstance().numServings + " servings");
        recipe_time.setText("Time: " +CurrentFragmentsSingleton.getInstance().cookTime + " minutes");
        restaurant_name.setText(CurrentFragmentsSingleton.getInstance().restaurantName);

        String priceEstimate;
        String dollarSigns = CurrentFragmentsSingleton.getInstance().restaurantPrice;
        if (dollarSigns.equals("$")) {
            priceEstimate = "$1-10";
        }
        else if (dollarSigns.equals("$$")) {
            priceEstimate = "$11-30";
        }
        else if (dollarSigns.equals("$$$")) {
            priceEstimate = "$31-60";
        }
        else {
            priceEstimate = "$61+";
        }
        restaurant_price.setText("Price: " + priceEstimate);
        //restaurant_time.setText(CurrentFragmentsSingleton.getInstance().res);

        double myLatitude = CurrentFragmentsSingleton.getInstance().myLatitude;
        double myLongitude = CurrentFragmentsSingleton.getInstance().myLongitude;
        String address = CurrentFragmentsSingleton.getInstance().address.replaceAll("\\s","+");
        String url = String.format("https://maps.googleapis.com/maps/api/distancematrix/" +
                "json?origins=%s,%s&destinations=%s&key=%s",myLatitude,myLongitude,address,getString(R.string.google_key));



        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    Log.d("response", response.toString());
                    String duration = response.getJSONArray("rows").getJSONObject(0)
                            .getJSONArray("elements").getJSONObject(0).getJSONObject("duration").getString("text");
                    Log.d("duration", duration);
                    restaurant_time.setText("Time: "+duration);
                }
                catch (Exception e){
                    Log.d("error", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };

        requestQueue.add(request);

        cook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment nextFragment = new RecipeEnd();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFragment)
                        .addToBackStack(null) //allow us to go back kind of maybe
                        .commit();
                CurrentFragmentsSingleton.getInstance().searchState = nextFragment;
            }
        });

        dine_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment nextFragment = new RestaurantEnd();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFragment)
                        .addToBackStack(null) //allow us to go back kind of maybe
                        .commit();
                CurrentFragmentsSingleton.getInstance().searchState = nextFragment;
            }
        });
        return view;
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
