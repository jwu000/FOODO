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
import android.widget.ListView;

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
    RequestQueue requestQueue;
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

        String searchTerm = CurrentFragmentsSingleton.getInstance().searchTerm;


        if ( ContextCompat.checkSelfPermission( getActivity(), Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( getActivity(), new String[] {  Manifest.permission.ACCESS_FINE_LOCATION  },
                    0);
        }

        if ( ContextCompat.checkSelfPermission( getActivity(), Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED )
        {
            final LocationManager locationMananger = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);


            locationMananger.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        Log.d("Location Changed", location.getLatitude() + " and " + location.getLongitude());
                        locationMananger.removeUpdates(this);
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
            Location location = locationMananger.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();

        }

        Log.d("long", String.valueOf(longitude));
        Log.d("lat", String.valueOf(latitude));

        String url = String.format("https://api.yelp.com/v3/businesses/search?term=%s&longitude=%s&latitude=%s&price=1,2,3,4",
                searchTerm,longitude,latitude);

        if (listOfRestaurants.size() == 0) {
            JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
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
                                    RestaurantResultAdapterItem restaurantItem = new RestaurantResultAdapterItem(restaurantName,rating,restuaurantId,price,address,distance);
                                    listOfRestaurants.add(restaurantItem);
                                }

                                restaurantResults = v.findViewById(R.id.restaurant_results);
                                AdapterRestaurantResults  myAdapter = new AdapterRestaurantResults(getActivity(),listOfRestaurants);
                                restaurantResults.setAdapter(myAdapter);
                                restaurantResults.setOnItemClickListener(new ListView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        Fragment nextFragment = new RestaurantPage();
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
            AdapterRestaurantResults  myAdapter = new AdapterRestaurantResults(getActivity(),listOfRestaurants);
            restaurantResults.setAdapter(myAdapter);
            restaurantResults.setOnItemClickListener(new ListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Fragment nextFragment = new RestaurantPage();
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
