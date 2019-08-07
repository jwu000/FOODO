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
import android.widget.TextView;

import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button yes;
    Button no;

    TextView rating;
    TextView restaurantName;
    TextView price;

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
        View view = inflater.inflate(R.layout.fragment_restaurant_page, container, false);

        final Bundle restaurantInfo = getArguments();
        rating = view.findViewById(R.id.rating_num_restaurant_page);
        restaurantName = view.findViewById(R.id.restaurant_name_restaurant_page);
        price = view.findViewById(R.id.price_dollarsign);

        rating.setText(new DecimalFormat("#.##").format(restaurantInfo.getDouble("rating")));
        restaurantName.setText(restaurantInfo.getString("restaurantName"));
        price.setText(restaurantInfo.getString("restaurantPrice"));

        yes = view.findViewById(R.id.restaurant_yes);
        no = view.findViewById(R.id.restaurant_no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pass selected restaurant info into CurrentFragmentsSingleton for track
                CurrentFragmentsSingleton.getInstance().restaurantName = restaurantInfo.getString("restaurantName");
                CurrentFragmentsSingleton.getInstance().restaurantPrice = restaurantInfo.getString("restaurantPrice");
                CurrentFragmentsSingleton.getInstance().rating = restaurantInfo.getDouble("rating");
                CurrentFragmentsSingleton.getInstance().address = restaurantInfo.getString("address");
                CurrentFragmentsSingleton.getInstance().distance = restaurantInfo.getString("distance");
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


}
