package com.foodoapp.foodo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass. fragment for when user chooses to eat out and goto the selected restauarnt
 */
public class RestaurantEnd extends Fragment implements OnMapReadyCallback {


    DatabaseReference dbRootReference;
    private DatabaseReference dbChildReference;
    private DatabaseReference dbUserReference;
    private DatabaseReference dbFavoriteReference;
    private DatabaseReference dbRestaurantReference;

    TextView restaurantName;
    Button restaurant_time;
    MapView mapview_end;
    TextView direction;
    RadioButton add_favorite_restaurant;

    public RestaurantEnd() {
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
        View view = inflater.inflate(R.layout.fragment_restaurant_end, container, false);
        dbRootReference = FirebaseDatabase.getInstance().getReference();
        dbChildReference = dbRootReference.child("users");
        dbUserReference = dbChildReference.child(CurrentSessionInfoSingleton.getInstance().user);
        dbFavoriteReference = dbUserReference.child("favorites");
        dbRestaurantReference = dbFavoriteReference.child("restaurants");

        restaurantName = view.findViewById(R.id.restaurant_name_end);
        restaurant_time = view.findViewById(R.id.restaurant_time);
        mapview_end = view.findViewById(R.id.restaurant_mapView_end);
        direction = view.findViewById(R.id.get_direction);
        add_favorite_restaurant = view.findViewById(R.id.restaurant_favorite_yes);

        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewEndBundle = null;
        if (savedInstanceState != null) {
            mapViewEndBundle = savedInstanceState.getBundle("MapViewBundleKey");
        }
        mapview_end.onCreate(mapViewEndBundle);
        mapview_end.getMapAsync(this);

        direction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                getActivity().startActivity(intent);
            }
        });


        restaurantName.setText(CurrentSessionInfoSingleton.getInstance().restaurantName);

        // restarts the process to beginning once user finishes
        restaurant_time.setOnClickListener(new View.OnClickListener() {
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
                historyData.put("type", "restaurant");
                historyData.put("name", CurrentSessionInfoSingleton.getInstance().restaurantName);
                newHistory.setValue(historyData);
            }
        });

        // addes restauarnt as favorite to database for this user
        add_favorite_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save info to favorite db
                String name = CurrentSessionInfoSingleton.getInstance().restaurantName;
                String address = CurrentSessionInfoSingleton.getInstance().address;
                double latitude = CurrentSessionInfoSingleton.getInstance().latitude;
                double longtitude = CurrentSessionInfoSingleton.getInstance().longtitude;

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("address", address);
                hashMap.put("latitude", latitude);
                hashMap.put("longtitude", longtitude);

                dbRestaurantReference.child(name).setValue(hashMap);
            }
        });
        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewEndBundle = outState.getBundle("MapViewBundleKey");
        if (mapViewEndBundle == null) {
            mapViewEndBundle = new Bundle();
            outState.putBundle("MapViewBundleKey", mapViewEndBundle);
        }

        mapview_end.onSaveInstanceState(mapViewEndBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapview_end.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapview_end.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapview_end.onStop();
    }

    @Override
    public void onMapReady(GoogleMap mapViewEnd) {
        LatLng theRestaurant = new LatLng(CurrentSessionInfoSingleton.getInstance().latitude, CurrentSessionInfoSingleton.getInstance().longtitude);
        mapViewEnd.addMarker(new MarkerOptions().position(theRestaurant).title("Marker"));
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
        mapViewEnd.setMyLocationEnabled(true);
        mapViewEnd.moveCamera(CameraUpdateFactory.newLatLngZoom(theRestaurant,12.0f));

    }

    @Override
    public void onPause() {
        mapview_end.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapview_end.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapview_end.onLowMemory();
    }





}
