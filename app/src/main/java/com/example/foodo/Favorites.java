package com.example.foodo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import util.AdapterFavoriteList;
import util.FavoriteAdapterItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class Favorites extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DatabaseReference dbRootReference;
    private DatabaseReference dbChildReference;
    private DatabaseReference dbUserReference;
    private DatabaseReference dbFavoriteReference;
    private DatabaseReference dbRecipeReference;
    private DatabaseReference dbRestaurantReference;


    ListView favoriteList;

    ArrayList<FavoriteAdapterItem> listofFavoriteItems = new ArrayList<FavoriteAdapterItem>();
    AdapterFavoriteList myAdapter;

    HashMap<String, Object> result;
    Bundle recipeInfo;
    Bundle restaurantInfo;
    Fragment nextFragment;

    public Favorites() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Favorites.
     */
    // TODO: Rename and change types and number of parameters
    public static Favorites newInstance(String param1, String param2) {
        Favorites fragment = new Favorites();
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
        dbRootReference = FirebaseDatabase.getInstance().getReference();
        dbRootReference = FirebaseDatabase.getInstance().getReference();
        dbChildReference = dbRootReference.child("users");
        dbUserReference = dbChildReference.child(CurrentFragmentsSingleton.getInstance().user);
        dbFavoriteReference = dbUserReference.child("favorites");
        dbRestaurantReference = dbFavoriteReference.child("restaurants");
        dbRecipeReference = dbFavoriteReference.child("recipes");

        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        favoriteList = view.findViewById(R.id.favorite_list);

        myAdapter = new AdapterFavoriteList(getActivity(), listofFavoriteItems);
        favoriteList.setAdapter(myAdapter);

        if(listofFavoriteItems.size() == 0) {
            dbRecipeReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    listofFavoriteItems.add(new FavoriteAdapterItem("recipe", dataSnapshot.getKey()));
                    favoriteList.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            dbRestaurantReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    listofFavoriteItems.add(new FavoriteAdapterItem("restaurant", dataSnapshot.getKey()));
                    favoriteList.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        favoriteList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(listofFavoriteItems.get(i).getType().equals("recipe")){
                    nextFragment = new FavoriteRecipe();
                    recipeInfo = new Bundle();
                    final String name = listofFavoriteItems.get(i).getName();

                    dbRecipeReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           result = (HashMap<String, Object>) dataSnapshot.child(name).getValue();
                           recipeInfo.putString("recipe_name", name);
                           recipeInfo.putInt("cookTime", Integer.parseInt(String.valueOf(result.get("time"))));
                           recipeInfo.putString("totalPrice", (String) result.get("price"));
                           recipeInfo.putInt("numServings", Integer.parseInt(String.valueOf(result.get("servings"))));
                           recipeInfo.putString("instructions", (String) result.get("steps"));
                           recipeInfo.putString("ingredients", (String) result.get("ingredients"));
                           nextFragment.setArguments(recipeInfo);
                           getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, nextFragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }else{
                    nextFragment = new FavoriteRestaurant();
                    restaurantInfo = new Bundle();
                    final String name = listofFavoriteItems.get(i).getName();

                    dbRestaurantReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            result = (HashMap<String, Object>) dataSnapshot.child(name).getValue();
                            restaurantInfo.putString("restaurantName", name);
                            restaurantInfo.putString("address", (String) result.get("address"));
                            restaurantInfo.putDouble("longitude", Double.parseDouble(String.valueOf(result.get("longtitude"))));
                            restaurantInfo.putDouble("latitude", Double.parseDouble(String.valueOf(result.get("latitude"))));
                            nextFragment.setArguments(restaurantInfo);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, nextFragment)
                                    .addToBackStack(null)
                                    .commit();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

            }
        });

        return view;
    }

}
