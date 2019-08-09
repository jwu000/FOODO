package com.example.foodo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import util.AdapterRestaurantResults;
import util.RestaurantResultAdapterItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantResults extends Fragment {

    ListView restaurantResults;
    double longitude = 0.0;
    double latitude = 0.0;
    Spinner restaurant_sort;
    RequestQueue requestQueue;
    AdapterRestaurantResults  myAdapter;
    ArrayList<RestaurantResultAdapterItem> listOfRestaurants = new ArrayList<>();

    public RestaurantResults() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_restaurant_results, container, false);

        requestQueue = Volley.newRequestQueue(getActivity());

        restaurant_sort = (Spinner) v.findViewById(R.id.sort_restaurant);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.sort_dropdown, android.R.layout.simple_spinner_item);
        restaurant_sort.setAdapter(adapter);

        String searchTerm = CurrentFragmentsSingleton.getInstance().searchTerm;

        Log.d("long", String.valueOf(CurrentFragmentsSingleton.getInstance().myLongitude));
        Log.d("lat", String.valueOf(CurrentFragmentsSingleton.getInstance().myLatitude));

        String url = String.format("https://api.yelp.com/v3/businesses/search?term=%s&longitude=%s&latitude=%s&price=1,2,3,4",
                searchTerm,CurrentFragmentsSingleton.getInstance().myLongitude,CurrentFragmentsSingleton.getInstance().myLatitude);

        if (listOfRestaurants.size() == 0) {
            JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.d("response", response.toString());
                            try{
                                JSONArray results = response.getJSONArray("businesses");
                                for (int index = 0; index < results.length(); index++) {
                                    JSONObject restaurant = results.getJSONObject(index);
                                    JSONObject location = restaurant.getJSONObject("location");
                                    String restuaurantId = restaurant.getString("id");
                                    Double rating = restaurant.getDouble("rating");
                                    String restaurantName = restaurant.getString("name");
                                    String price = restaurant.getString("price");

                                    String address = location.getString("address1") + " " + location.getString("city") + ", "
                                            + location.getString("state") + " " + location.getString("zip_code");
                                    String distance = new DecimalFormat("#.##").format(restaurant.getDouble("distance")/1000.0);
                                    double longitude = restaurant.getJSONObject("coordinates").getDouble("longitude");
                                    double latitude = restaurant.getJSONObject("coordinates").getDouble("latitude");
                                    RestaurantResultAdapterItem restaurantItem = new RestaurantResultAdapterItem(restaurantName,rating,restuaurantId,price,address,distance,longitude,latitude);
                                    listOfRestaurants.add(restaurantItem);
                                }

                                restaurantResults = v.findViewById(R.id.restaurant_results);
                                myAdapter = new AdapterRestaurantResults(getActivity(),listOfRestaurants);
                                restaurantResults.setAdapter(myAdapter);
                                restaurant_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        Collections.sort(listOfRestaurants, new Comparator<RestaurantResultAdapterItem>() {
                                            @Override
                                            public int compare(RestaurantResultAdapterItem restaurantResultAdapterItem, RestaurantResultAdapterItem t1) {
                                                return restaurantResultAdapterItem.getPrice().compareTo(t1.getPrice());

                                            }
                                        });
                                        myAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                                restaurantResults.setOnItemClickListener(new ListView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        Fragment nextFragment = new RestaurantPage();
                                        Bundle restaurantInfo = new Bundle();
                                        RestaurantResultAdapterItem theRestaurant = listOfRestaurants.get(i);
                                        restaurantInfo.putString("restaurantId", theRestaurant.getRestaurantId());
                                        restaurantInfo.putString("restaurantName", theRestaurant.getRestaurantName());
                                        restaurantInfo.putString("restaurantPrice", theRestaurant.getPrice());
                                        restaurantInfo.putDouble("rating", theRestaurant.getRating());
                                        restaurantInfo.putString("address", theRestaurant.getAddress());
                                        restaurantInfo.putString("distance", theRestaurant.getDistance());
                                        restaurantInfo.putDouble("longitude", theRestaurant.getLongitude());
                                        restaurantInfo.putDouble("latitude", theRestaurant.getLatitude());
                                        nextFragment.setArguments(restaurantInfo);
                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.fragment_container, nextFragment)
                                                .addToBackStack(null) //allow us to go back kind of maybe
                                                .commit();
                                        CurrentFragmentsSingleton.getInstance().searchState = nextFragment;
                                    }
                                });

                            } catch (Exception e) {
                                Log.d("error", e.toString());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("resquestFAIL", error.toString());
                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("Authorization", "Bearer"+" "+getString(R.string.yelp_key));
                    return headers;
                }
            };

            requestQueue.add(objectRequest);
        }
        else {
            restaurantResults = v.findViewById(R.id.restaurant_results);
            myAdapter = new AdapterRestaurantResults(getActivity(),listOfRestaurants);
            restaurantResults.setAdapter(myAdapter);
            restaurant_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Collections.sort(listOfRestaurants, new Comparator<RestaurantResultAdapterItem>() {
                        @Override
                        public int compare(RestaurantResultAdapterItem restaurantResultAdapterItem, RestaurantResultAdapterItem t1) {
                            return restaurantResultAdapterItem.getPrice().compareTo(t1.getPrice());

                        }
                    });
                    myAdapter.notifyDataSetChanged();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            restaurantResults.setOnItemClickListener(new ListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Fragment nextFragment = new RestaurantPage();
                    Bundle restaurantInfo = new Bundle();
                    RestaurantResultAdapterItem theRestaurant = listOfRestaurants.get(i);
                    restaurantInfo.putString("restaurantId", theRestaurant.getRestaurantId());
                    restaurantInfo.putString("restaurantName", theRestaurant.getRestaurantName());
                    restaurantInfo.putString("restaurantPrice", theRestaurant.getPrice());
                    restaurantInfo.putDouble("rating", theRestaurant.getRating());
                    restaurantInfo.putString("address", theRestaurant.getAddress());
                    restaurantInfo.putString("distance", theRestaurant.getDistance());
                    restaurantInfo.putDouble("longitude", theRestaurant.getLongitude());
                    restaurantInfo.putDouble("latitude", theRestaurant.getLatitude());
                    nextFragment.setArguments(restaurantInfo);
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



}
