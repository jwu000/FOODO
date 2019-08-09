package com.example.foodo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.CrossProcessCursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.AdapterReviews;
import util.ReviewAdapterItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantPage extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView restaurantName;
    TextView address;
    ListView reviews;
    Button yes;
    Button no;
    String restaurantId;
    TextView rating;
    TextView price;
    MapView map;

    RequestQueue requestQueue;
    ArrayList<ReviewAdapterItem> listOfReviews = new ArrayList<>();

    ListView reviewsList;

    public RestaurantPage() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantPage.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantPage newInstance(String param1, String param2) {
        RestaurantPage fragment = new RestaurantPage();
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
        final View view = inflater.inflate(R.layout.fragment_restaurant_page, container, false);
        final Bundle restaurantInfo = getArguments();

        yes = view.findViewById(R.id.restaurant_yes);
        no = view.findViewById(R.id.restaurant_no);
        reviews = view.findViewById(R.id.reviews_results);
        rating = view.findViewById(R.id.rating_num_restaurant_page);
        restaurantName = view.findViewById(R.id.restaurant_name_restaurant_page);
        price = view.findViewById(R.id.price_dollarsign);
        address = view.findViewById(R.id.address);
        map = view.findViewById(R.id.restaurant_map);

        rating.setText(new DecimalFormat("#.##").format(restaurantInfo.getDouble("rating")));
        restaurantName.setText(restaurantInfo.getString("restaurantName"));
        price.setText(restaurantInfo.getString("restaurantPrice"));
        restaurantId = restaurantInfo.getString("restaurantId");
        address.setText(restaurantInfo.getString("address"));

        requestQueue = Volley.newRequestQueue(getActivity());

        String url = String.format("https://api.yelp.com/v3/businesses/%s/reviews", restaurantId);

        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle("MapViewBundleKey");
        }
        map.onCreate(mapViewBundle);
        map.getMapAsync(this);

        if (listOfReviews.size() == 0) {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray reviews = response.getJSONArray("reviews");
                                for (int index = 0; index < reviews.length(); index++) {
                                    JSONObject aReview = reviews.getJSONObject(index);
                                    int rating = aReview.getInt("rating");
                                    String reviewerName = aReview.getJSONObject("user").getString("name");
                                    String reviewText = aReview.getString("text");
                                    String timeCreated = aReview.getString("time_created");
                                    ReviewAdapterItem reviewItem = new ReviewAdapterItem(rating, reviewerName, reviewText, timeCreated);
                                    listOfReviews.add(reviewItem);
                                }
                                reviewsList = view.findViewById(R.id.reviews_results);
                                AdapterReviews myAdapter = new AdapterReviews(getActivity(), listOfReviews);
                                reviewsList.setAdapter(myAdapter);


                            } catch (Exception e) {
                                Log.d("error", e.toString());
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("errorResponse", error.toString());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("Authorization", "Bearer" + " " + getString(R.string.yelp_key));
                    return headers;
                }
            };

            requestQueue.add(request);
        }

        else {
            reviewsList = view.findViewById(R.id.reviews_results);
            AdapterReviews myAdapter = new AdapterReviews(getActivity(), listOfReviews);
            reviewsList.setAdapter(myAdapter);
        }

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pass selected restaurant info into CurrentFragmentsSingleton for track
                CurrentFragmentsSingleton.getInstance().restaurantName = restaurantInfo.getString("restaurantName");
                CurrentFragmentsSingleton.getInstance().restaurantPrice = restaurantInfo.getString("restaurantPrice");
                CurrentFragmentsSingleton.getInstance().rating = restaurantInfo.getDouble("rating");
                CurrentFragmentsSingleton.getInstance().address = restaurantInfo.getString("address");
                CurrentFragmentsSingleton.getInstance().distance = restaurantInfo.getString("distance");
                CurrentFragmentsSingleton.getInstance().latitude = restaurantInfo.getDouble("latitude");
                CurrentFragmentsSingleton.getInstance().longtitude = restaurantInfo.getDouble("longitude");
                Fragment nextFragment = new Comparison();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFragment)
                        .addToBackStack(null) //allow us to go back kind of maybe
                        .commit();
                CurrentFragmentsSingleton.getInstance().searchState = nextFragment;
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
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


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle("MapViewBundleKey");
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle("MapViewBundleKey", mapViewBundle);
        }

        map.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        map.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        map.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng theRestaurnt = new LatLng(getArguments().getDouble("latitude"), getArguments().getDouble("longitude"));
        map.addMarker(new MarkerOptions().position(theRestaurnt).title("Marker"));
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(theRestaurnt,12.0f));


    }

    @Override
    public void onPause() {
        map.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        map.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map.onLowMemory();
    }
}
