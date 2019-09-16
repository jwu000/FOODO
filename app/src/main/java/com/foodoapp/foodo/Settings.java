package com.foodoapp.foodo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Settings extends Fragment {

    EditText userFeedback;
    Button logoutButton;
    Button submitFeedbackButton;

    public Settings() {
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
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        userFeedback = v.findViewById(R.id.user_feedback_text);
        logoutButton = v.findViewById(R.id.logout_button);
        submitFeedbackButton = v.findViewById(R.id.submit_feedback_button);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        submitFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feedback = userFeedback.getText().toString();
                DatabaseReference dbFeedbackRef = FirebaseDatabase.getInstance().getReference().child("feedback");
                DatabaseReference dbNewFeedbackRef = dbFeedbackRef.push();
                dbNewFeedbackRef.setValue(feedback);
                userFeedback.setText("");


            }
        });


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
