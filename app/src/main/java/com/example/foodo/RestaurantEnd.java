package com.example.foodo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapView;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantEnd extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView restaurantName;
    Button restaurant_time;
    MapView mapview_end;
    TextView direction;


    public RestaurantEnd() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantEnd.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantEnd newInstance(String param1, String param2) {
        RestaurantEnd fragment = new RestaurantEnd();
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
        View view = inflater.inflate(R.layout.fragment_restaurant_end, container, false);
        restaurantName = view.findViewById(R.id.restaurant_name_end);
        restaurant_time = view.findViewById(R.id.restaurant_time);
        mapview_end = view.findViewById(R.id.restaurant_mapView_end);
        direction = view.findViewById(R.id.get_direction);

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


        restaurantName.setText(CurrentFragmentsSingleton.getInstance().restaurantName);
        restaurant_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment nextFragment = new SearchRecipe();
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
        LatLng theRestaurant = new LatLng(CurrentFragmentsSingleton.getInstance().latitude, CurrentFragmentsSingleton.getInstance().longtitude);
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


//    public void OnClickListener() {
//        String uri = "geo:" + CurrentFragmentsSingleton.getInstance().latitude + "," + CurrentFragmentsSingleton.getInstance().longtitude;
//        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
//        getActivity().startActivity(intent);
//    }



}
