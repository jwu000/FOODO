package com.example.foodo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import util.AdapterHistory;
import util.HistoryAdapterItem;

/**
 *  Fragment for user history. Displays in chronological order user's past decisions.
 */
public class History extends Fragment {



    ArrayList<HistoryAdapterItem> listHistory = new ArrayList<HistoryAdapterItem>();
    AdapterHistory myAdapter;

    ListView historyList;

    public History() {
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
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        DatabaseReference users = FirebaseDatabase.getInstance().getReference().child("users");

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference history = users.child(CurrentSessionInfoSingleton.getInstance().user).child("history");
        historyList = v.findViewById(R.id.history_list);
        myAdapter = new AdapterHistory(getActivity(),listHistory);
        historyList.setAdapter(myAdapter);

        if (listHistory.size() == 0) {
            history.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Log.d("time", dataSnapshot.getKey());
                    HashMap<String,String> historyData = (HashMap<String,String>)dataSnapshot.getValue();
                    Log.d("name", historyData.get("name"));
                    HistoryAdapterItem historyItem = new HistoryAdapterItem(historyData.get("name"),
                            historyData.get("type"), dataSnapshot.getKey());
                    listHistory.add(0,historyItem);
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



}
